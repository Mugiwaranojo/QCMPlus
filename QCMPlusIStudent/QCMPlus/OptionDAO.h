//
//  OptionDAO.h
//  QCMPlus
//
//  Created by fitec on 28/07/2014.
//  Copyright (c) 2014 Guillaume Brunier. All rights reserved.
//

#import "ParseDAO.h"
#import "Question.h"

@interface OptionDAO : ParseDAO

typedef void(^OnFindOptionByQuestionCompleted)(NSError * error, NSArray * options);

+ (instancetype)sharedInstance;

+ (NSString *)TABLE_NAME;

- (NSString *)tableName;
 
- (void) findOptionsByQuestion:(Question *)question callback:(OnFindOptionByQuestionCompleted)callback;

@end
