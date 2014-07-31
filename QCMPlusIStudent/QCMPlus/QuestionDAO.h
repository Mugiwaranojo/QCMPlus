//
//  QuestionDAO.h
//  QCMPlus
//
//  Created by fitec on 23/07/2014.
//  Copyright (c) 2014 Guillaume Brunier. All rights reserved.
//

#import "ParseDAO.h"

#import "Question.h"
#import "MCQ.h"
#import "User.h"

@interface QuestionDAO : ParseDAO

typedef void(^OnFindQuestionsByMCQCompleted)(NSError * error, NSArray * questions);

+ (instancetype)sharedInstance;

+ (NSString *)TABLE_NAME;

- (NSString *)tableName;

- (void)findQuestionsByMCQ:(MCQ *)mcq
                  callback:(OnFindQuestionsByMCQCompleted)callback;
@end
