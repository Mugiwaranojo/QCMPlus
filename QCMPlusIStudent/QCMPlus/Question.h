//
//  Question.h
//  QCMPlus
//
//  Created by fitec on 23/07/2014.
//  Copyright (c) 2014 Guillaume Brunier. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "UserAnswer.h"

@interface Question : NSObject

@property (nonatomic, strong) NSString * objectId;
@property (nonatomic, strong) NSString * statement;
@property (nonatomic, strong) NSArray * options;
@property (nonatomic, strong) UserAnswer * userAnswer;

@end
