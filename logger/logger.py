#!/usr/bin/env python3

from networktables import NetworkTables
from networktables.util import ntproperty

import queue
import json
import time
import threading

import logging
logging.basicConfig(level=logging.DEBUG)


class DataLogger:
    
    matchNumber = ntproperty('/FMSInfo/MatchNumber', 0, False)
    eventName = ntproperty('/FMSInfo/EventName', 'unknown', False)
    
    def __init__(self):
        self.queue = queue.Queue()
        self.mode = 'disabled'
        self.data = []
        self.lock = threading.Lock()
    
    def connectionListener(self, connected, info):
        print(info, '; Connected=%s' % connected)
        
        if not connected:
            self.valueChanged('/robot/mode', 'disabled', False)
        
    def valueChanged(self, key, value, isNew):
        if key == '/robot/mode':
            
            with self.lock:
                last = self.mode
                self.mode = value
                
                data = self.data
                self.data = []
            
            # only store on auto -> disabled transition
            if last == 'auto':
                
                tm = time.strftime('%Y%m%d-%H%M-%S')
                name = '%s-%s-%s.json' % (tm, self.eventName, int(self.matchNumber))
                print('Writing', name)
                
                self.queue.put((name, data))
        
        elif key == '/SmartDashboard/pfdebug':
            if self.mode != 'disabled':
                with self.lock:
                    self.data.append(value)
    
    def run(self):
        
        NetworkTables.addConnectionListener(self.connectionListener, immediateNotify=True)
        NetworkTables.addEntryListener(self.valueChanged)
        
        while True:
            name, data = self.queue.get()
            with open(name, 'w') as fp:
                json.dump(data, fp)

if __name__ == '__main__':
    
    NetworkTables.initialize(server='localhost')
    
    log = DataLogger()
    log.run()
