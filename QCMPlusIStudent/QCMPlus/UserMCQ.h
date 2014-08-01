//
//  UserMCQ.h
//  QCMPlus
//
//  Created by fitec on 28/07/2014.
//  Copyright (c) 2014 Guillaume Brunier. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "MCQ.h"

@interface UserMCQ : NSObject

@property (nonatomic, strong) NSString * objectId;
@property (nonatomic, strong) NSEnumerator * state;
@property (nonatomic, assign) int timeSpent;
@property (nonatomic, assign) int score;
@property (nonatomic, strong) NSDate * dateCreated;
@property (nonatomic, strong) NSDate * dateUpdated;
@property (nonatomic, strong) MCQ * mcq;

@end
