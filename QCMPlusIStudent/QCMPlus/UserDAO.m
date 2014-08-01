//
//  UserDAO.m
//  QCMPlus
//
//  Created by fitec on 23/07/2014.
//  Copyright (c) 2014 Guillaume Brunier. All rights reserved.
//

#import "UserDAO.h"

static NSString * TABLE_NAME = @"User";

@implementation UserDAO

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
    return [UserDAO TABLE_NAME];
}

- (void)performLoginWithUserName:(NSString *)username andPassword:(NSString *)password callback:(OnLoginQueryCompleted)callback
{
    PFQuery * q = [PFQuery queryWithClassName:[UserDAO TABLE_NAME]];
    [q whereKey:@"login" equalTo:username];
    [q whereKey:@"password" equalTo:password];
    
    [q findObjectsInBackgroundWithBlock:^(NSArray *objects, NSError *error) {
        if (!error && [objects count]) {
            PFObject * user = [objects objectAtIndex:0];
            callback(YES,  (User *)[self parsePFObject:user]);
        } else {
            callback(NO, nil);
        }
    }];
}

- (NSObject *)parsePFObject:(PFObject *)pfObject
{
    User * u        = [[User alloc] init];
    u.firstname     = pfObject[@"firstname"];
    u.lastname      = pfObject[@"lastname"];
    u.compagny      = pfObject[@"compagny"];
    u.login         = pfObject[@"login"];
    u.password      = pfObject[@"password"];
    u.objectId      = pfObject.objectId;
    
    return u;
}

+ (NSString *)tableName
{
    return @"User";
}

@end
