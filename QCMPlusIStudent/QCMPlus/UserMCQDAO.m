//
//  UserMCQDAO.m
//  QCMPlus
//
//  Created by fitec on 28/07/2014.
//  Copyright (c) 2014 Guillaume Brunier. All rights reserved.
//

#import "UserMCQDAO.h"
#import "UserDAO.h"
#import "MCQDAO.h"

static NSString * TABLE_NAME = @"UserMCQ";

@implementation UserMCQDAO

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
    return [UserMCQDAO TABLE_NAME];
}

- (NSObject *)parsePFObject:(PFObject *)pfObject
{
    UserMCQ * obj = [[UserMCQ alloc] init];
    obj.objectId    = pfObject.objectId;
    obj.timeSpent   = [pfObject[@"timeSpent"] intValue];
    obj.score       = 0;
    obj.dateCreated   = pfObject.updatedAt;
    obj.dateUpdated   = pfObject.createdAt;
    
    return obj;
}

- (void)findUserMCQsByUser:(User *)user
                  callback:(OnFindUserMCQsByUserCompleted)callback
{
    PFQuery * qUser = [PFQuery queryWithClassName:[UserDAO TABLE_NAME]];
    [qUser whereKey:@"objectId" equalTo:user.objectId];
    
    PFQuery * q = [PFQuery queryWithClassName:[UserMCQDAO TABLE_NAME]];
    [q whereKey:@"user" matchesQuery:qUser];
    
    [q findObjectsInBackgroundWithBlock:^(NSArray *objects, NSError *error) {
        if (!error) {
            
            NSMutableArray * list = [NSMutableArray new];
            
            for (PFObject * obj in objects) {
                [list addObject:[self parsePFObject:obj]];
            }
            
            callback(nil, [NSArray arrayWithArray:list]);
        }
        else {
            callback(error, nil);
        }
    }];
}

- (void)findUserMCQByUser:(User *)user
                   andMCQ:(MCQ *)mcq
                 callback:(OnFindUserMCQByUserAndPasswordCompleted)callback
{
    PFQuery * qUser = [PFQuery queryWithClassName:[UserDAO TABLE_NAME]];
    [qUser whereKey:@"objectId" equalTo:user.objectId];
    
    PFQuery * qMCQ = [PFQuery queryWithClassName:[MCQDAO TABLE_NAME]];
    [qMCQ whereKey:@"objectId" equalTo:mcq.objectId];
    
    PFQuery * q = [PFQuery queryWithClassName:[UserMCQDAO TABLE_NAME]];
    [q whereKey:@"user" matchesQuery:qUser];
    [q whereKey:@"mcq" matchesQuery:qMCQ];
    
    [q findObjectsInBackgroundWithBlock:^(NSArray *objects, NSError *error) {
        if (!error) {
            
            PFObject * obj = [objects objectAtIndex:0];
            callback(nil, (UserMCQ *)[self parsePFObject:obj]);
        }
        else {
            callback(error, nil);
        }
    }];
}

@end
