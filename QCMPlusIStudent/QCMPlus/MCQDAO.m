//
//  MCQDAO.m
//  QCMPlus
//
//  Created by fitec on 23/07/2014.
//  Copyright (c) 2014 Guillaume Brunier. All rights reserved.
//

#import "MCQDAO.h"
#import "UserDAO.h"
#import "UserMCQDAO.h"

static NSString * TABLE_NAME = @"MCQ";

@implementation MCQDAO

+ (instancetype)sharedInstance
{
    static id __SharedInstance = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        __SharedInstance = [[self alloc] init];
    });
    return __SharedInstance;
}

- (NSObject *)parsePFObject:(PFObject *)pfObject
{
    MCQ * obj       = [MCQ new];
    obj.objectId    = pfObject.objectId;
    obj.name        = pfObject[@"name"];
    obj.summary     = pfObject[@"summary"];

    return obj;
}

+ (NSString *)TABLE_NAME
{
    return TABLE_NAME;
}

- (NSString *)tableName
{
    return [MCQDAO TABLE_NAME];
}

- (void) findMCQbyUserMCQ:(UserMCQ *)userMCQ callback:(OnFindMCQbyUserMCQCompleted)callback
{
    PFQuery * q = [PFQuery queryWithClassName:[UserMCQDAO TABLE_NAME]];
    [q whereKey:@"objectId" equalTo:userMCQ.objectId];
    [q includeKey:@"mcq"];
    
    [q findObjectsInBackgroundWithBlock:^(NSArray *objects, NSError *error) {
        if (!error) {
            PFObject * obj = [objects objectAtIndex:0];
            callback(nil, (MCQ *)[self parsePFObject:[obj objectForKey:@"mcq"]]);
        } else {
            callback(error, nil);
        }
    }];
}

- (void) findMCQsByUser:(User *)user withState:(NSString *)state callback:(OnFindMCQsByUserCompleted)callback
{
    PFQuery * q = [PFQuery queryWithClassName:[UserMCQDAO TABLE_NAME]];
    [q whereKey:@"user" equalTo:[PFObject objectWithoutDataWithClassName:[UserDAO TABLE_NAME] objectId:user.objectId]];
    [q whereKey:@"state" equalTo:state];
    [q includeKey:@"mcq"];
    
    [q findObjectsInBackgroundWithBlock:^(NSArray *objects, NSError *error) {
        if (!error) {
            
            NSMutableArray * list = [NSMutableArray new];
            
            for (PFObject * obj in objects) {
                [list addObject:[self parsePFObject:[obj objectForKey:@"mcq"]]];
            }
            
            callback(nil, [NSArray arrayWithArray:list]);
        }
        else {
            callback(error, nil);
        }
    }];
}
            
@end
