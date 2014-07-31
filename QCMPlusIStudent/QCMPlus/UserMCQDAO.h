//
//  UserMCQDAO.h
//  QCMPlus
//
//  Created by fitec on 28/07/2014.
//  Copyright (c) 2014 Guillaume Brunier. All rights reserved.
//

#import "ParseDAO.h"
#import "UserMCQ.h"
#import "User.h"
#import "MCQ.h"

@interface UserMCQDAO : ParseDAO

typedef void(^OnFindUserMCQsByUserCompleted)(NSError * error, NSArray * userMCQs);
typedef void(^OnFindUserMCQByUserAndPasswordCompleted)(NSError * error, UserMCQ * userMCQ);

+ (instancetype)sharedInstance;

+ (NSString *)TABLE_NAME;

- (NSString *)tableName;

- (void)findUserMCQsByUser:(User *)user callback:(OnFindUserMCQsByUserCompleted)callback;
- (void)findUserMCQByUser:(User *)user andMCQ:(MCQ *)mcq callback:(OnFindUserMCQByUserAndPasswordCompleted)callback;

@end