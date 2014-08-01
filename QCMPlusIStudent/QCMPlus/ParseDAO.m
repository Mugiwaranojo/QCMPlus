//
//  ParseDAO.m
//  QCMPlus
//
//  Created by fitec on 28/07/2014.
//  Copyright (c) 2014 Guillaume Brunier. All rights reserved.
//

#import "ParseDAO.h"

@implementation ParseDAO

- (instancetype)init
{
    self = [super init];
    if (self) {
        [Parse setApplicationId:@"1XpHvksxkUpokKjgKINeQuzwCUAAkyFpwRHBZTz3"
                      clientKey:@"pJFS5zqqaBKosNs69n1MQxV8kVSta0y3c0NrRUJW"];
    }
    return self;
}

- (void)save:(NSObject *)entity callback:(OnSaveQueryCompleted) callback
{
    
}

- (void)remove:(NSObject *)entity callback:(OnRemoveQueryCompleted) callback
{
    
}

- (void)find:(NSString *)objectId callback:(OnFindQueryCompleted)callback
{
    PFQuery * q = [PFQuery queryWithClassName:[self tableName]];
    [q whereKey:@"objectId" equalTo:objectId];
    
    [q findObjectsInBackgroundWithBlock:^(NSArray *objects, NSError *error) {
        if (!error) {
            PFObject * obj = [objects objectAtIndex:0];
            callback(nil, [self parsePFObject:obj]);
            
        } else {
            callback(error, nil);
        }
    }];
}

- (void)findAll:(OnFindAllQueryCompleted) callback
{
    PFQuery * q = [PFQuery queryWithClassName:[self tableName]];
    
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

- (NSObject *)parsePFObject:(PFObject *)pfObject
{
    return [[NSObject alloc] init];
}

- (NSString *)tableName
{
    return @"Undefined";
}

@end
