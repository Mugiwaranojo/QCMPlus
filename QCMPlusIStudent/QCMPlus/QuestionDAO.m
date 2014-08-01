//
//  QuestionDAO.m
//  QCMPlus
//
//  Created by fitec on 23/07/2014.
//  Copyright (c) 2014 Guillaume Brunier. All rights reserved.
//

#import "QuestionDAO.h"
#import "MCQDAO.h"

static NSString * TABLE_NAME = @"Question";

@implementation QuestionDAO

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
    Question * obj  = [Question new];
    obj.objectId    = pfObject.objectId;
    obj.statement   = pfObject[@"statement"];
    
    return obj;
}

+ (NSString *)TABLE_NAME
{
    return TABLE_NAME;
}

- (NSString *)tableName
{
    return [QuestionDAO TABLE_NAME];
}

- (void)findQuestionsByMCQ:(MCQ *)mcq
                  callback:(OnFindQuestionsByMCQCompleted)callback
{
    PFQuery * qMCQ = [PFQuery queryWithClassName:[MCQDAO TABLE_NAME]];
    [qMCQ whereKey:@"objectId" equalTo:mcq.objectId];
    
    PFQuery * q = [PFQuery queryWithClassName:[QuestionDAO TABLE_NAME]];
    [q whereKey:@"mcq" matchesQuery:qMCQ];
    
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

@end
