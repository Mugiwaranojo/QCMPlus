//
//  IDAO.h
//  QCMPlus
//
//  Created by fitec on 28/07/2014.
//  Copyright (c) 2014 Guillaume Brunier. All rights reserved.
//

#import <Foundation/Foundation.h>

@protocol IDAO

typedef void(^OnFindQueryCompleted)(NSError * error, NSObject * object);
typedef void(^OnFindAllQueryCompleted)(NSError * error, NSArray * objects);
typedef void(^OnSaveQueryCompleted)(NSError * error, BOOL success);
typedef void(^OnRemoveQueryCompleted)(NSError * error, BOOL success);

- (void)save:(NSObject *)entity callback:(OnSaveQueryCompleted) callback;
- (void)remove:(NSObject *)entity callback:(OnRemoveQueryCompleted) callback;
- (void)find:(NSString *)objectId callback:(OnFindQueryCompleted) callback;
- (void)findAll:(OnFindAllQueryCompleted) callback;

@end
