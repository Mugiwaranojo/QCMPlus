//
//  MCQ.h
//  QCMPlus
//
//  Created by fitec on 23/07/2014.
//  Copyright (c) 2014 Guillaume Brunier. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface MCQ : NSObject

@property (nonatomic, strong) NSString * objectId;
@property (nonatomic, strong) NSString * name;
@property (nonatomic, strong) NSString * summary;
@property (nonatomic, strong) NSArray * questions;

@end
