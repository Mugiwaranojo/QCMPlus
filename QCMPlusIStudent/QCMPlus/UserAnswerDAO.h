//
//  UserAnswerDAO.h
//  QCMPlus
//
//  Created by fitec on 28/07/2014.
//  Copyright (c) 2014 Guillaume Brunier. All rights reserved.
//

#import "ParseDAO.h"

@interface UserAnswerDAO : ParseDAO

+ (instancetype)sharedInstance;

+ (NSString *)TABLE_NAME;

- (NSString *)tableName;

@end
