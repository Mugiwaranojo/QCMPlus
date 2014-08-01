//
//  UserAnswerDAO.m
//  QCMPlus
//
//  Created by fitec on 28/07/2014.
//  Copyright (c) 2014 Guillaume Brunier. All rights reserved.
//

#import "UserAnswerDAO.h"

static NSString * TABLE_NAME = @"UserAnswer";

@implementation UserAnswerDAO

+ (instancetype)sharedInstance
{
    static id __SharedInstance = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        __SharedInstance = [[self alloc] init];
    });
    return __SharedInstance;
}

+ (NSString *)TABLE_NAME
{
    return TABLE_NAME;
}

- (NSString *)tableName
{
    return [UserAnswerDAO TABLE_NAME];
}

@end