//
//  OptionDAO.m
//  QCMPlus
//
//  Created by fitec on 28/07/2014.
//  Copyright (c) 2014 Guillaume Brunier. All rights reserved.
//

#import "OptionDAO.h"

static NSString * TABLE_NAME = @"Option";

@implementation OptionDAO

+ (instancetype)sharedInstance
{
    static id __SharedInstance = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        __SharedInstance = [[self alloc] init];
    });
    return __SharedInstance;
}

- (void) findOptionsByQuestion:(Question *)question callback:(OnFindOptionByQuestionCompleted)callback
{
    
}

+ (NSString *)TABLE_NAME
{
    return TABLE_NAME;
}

- (NSString *)tableName
{
    return [OptionDAO TABLE_NAME];
}

@end
