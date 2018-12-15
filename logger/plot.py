
import json
import sys

import pprint

import numpy as np
import matplotlib

import pathfinder as pf

import matplotlib.pyplot as plt

if __name__ == '__main__':
    fname = sys.argv[1]
    with open(fname) as fp:
        data = json.load(fp)
    
    # fix time
    offset = data[0][0]
    
    for d in data:
        d[0] -= offset
        #
        d.append(pf.boundHalfDegrees(d[12] - d[11]))
    
    data = np.array(data)
    
    # make the data addressable by row
    data = data.transpose()
    
    # 0 Timer.getFPGATimestamp(),
    #
    # 1 outputLeftEncoder,
    # 2 l_encoder,
    # 3 l_distance_covered,
    # 4 lSegment.position,
    # 5 lSegment.velocity,
    #
    # 6 outputRightEncoder,
    # 7 r_encoder,
    # 8 r_distance_covered,
    # 9 rSegment.position,
    # 10 rSegment.velocity,
    #
    # 11 ang,
    # 12 desired_heading
    
    # 13
    # 14
    # 15
    # 16
    
    plt.suptitle(fname)
    
    plt.subplot2grid((3,1),(1,0))
    plt.plot(data[0], data[4] - data[3], data[0], data[9] - data[8])
    plt.title("Encoder error")
    
    plt.subplot2grid((3,1),(2,0))
    plt.plot(data[0], data[-1])
    plt.title("Gyro error")
    
    plt.subplot2grid((3,1),(0,0))
    #plt.plot(-data[14], data[13], -data[16], data[15])
    plt.plot(data[0], data[1], data[0], data[6])
    plt.title("Output")
    
    plt.show()
