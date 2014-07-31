//
//  MCQDAO.h
//  QCMPlus
//
//  Created by fitec on 23/07/2014.
//  Copyright (c) 2014 Guillaume Brunier. All rights reserved.
//

#import "ParseDAO.h"
#import "MCQ.h"
#import "UserMCQ.h"
#import "User.h"

@interface MCQDAO : ParseDAO

typedef void(^OnFindMCQbyUserMCQCompleted)(NSError * error, MCQ * mcq);
typedef void(^OnFindMCQsByUserCompleted)(NSError * error, NSArray * mcqs);

+ (instancetype)sharedInstance;

+ (NSString *)TABLE_NAME;

- (NSString *)tableName;

- (void) findMCQbyUserMCQ:(UserMCQ *)userMCQ callback:(OnFindMCQbyUserMCQCompleted)callback;

- (void) findMCQsByUser:(User *)user withState:(NSString *)state callback:(OnFindMCQsByUserCompleted)callback;

@end
