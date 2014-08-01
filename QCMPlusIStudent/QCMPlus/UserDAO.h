//
//  UserDAO.h
//  QCMPlus
//
//  Created by fitec on 23/07/2014.
//  Copyright (c) 2014 Guillaume Brunier. All rights reserved.
//

#import "ParseDAO.h"
#import "User.h"

@interface UserDAO : ParseDAO

typedef void(^OnLoginQueryCompleted)(BOOL success, User * user);

- (void) performLoginWithUserName:(NSString *)username andPassword:(NSString *)password callback:(OnLoginQueryCompleted)callback;

+ (instancetype)sharedInstance;

+ (NSString *)TABLE_NAME;

- (NSString *)tableName;

@end
