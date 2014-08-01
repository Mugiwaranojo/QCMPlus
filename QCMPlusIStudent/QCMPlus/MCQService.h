//
//  MCQService.h
//  QCMPlus
//
//  Created by fitec on 29/07/2014.
//  Copyright (c) 2014 Guillaume Brunier. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "UserMCQ.h"
#import "User.h"

@interface MCQService : NSObject

typedef void(^OnLoginCompleted)(BOOL success);
typedef void(^OnDoneMCQsFetched)(BOOL success, NSArray * mcqs);
typedef void(^OnAvailableMCQsFetched)(BOOL success, NSArray * mcqs);
typedef void(^OnMCQDetailsFetched)(BOOL success, UserMCQ * userMCQ);
typedef void(^OnMCQQuestionsDetailFetched)(BOOL success, NSArray * questions);

+ (instancetype) sharedInstance;

- (void)connectWithLogin:(NSString *)login
             andPassword:(NSString *)password
                callback:(OnLoginCompleted)callback;

- (void)fetchDoneMCQs:(OnDoneMCQsFetched)callback;

- (void)fetchAvailableMCQs:(OnAvailableMCQsFetched)callback;

- (void)fetchMCQDetails:(MCQ *)mcq
               callback:(OnMCQDetailsFetched)callback;

- (void)fetchMCQQuestionsDetail:(MCQ *)mcq
                       callback:(OnMCQQuestionsDetailFetched)callback;

@end