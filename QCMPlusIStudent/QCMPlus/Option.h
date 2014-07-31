//
//  Option.h
//  QCMPlus
//
//  Created by fitec on 28/07/2014.
//  Copyright (c) 2014 Guillaume Brunier. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Option : NSObject

@property (nonatomic, strong) NSString * objectId;
@property (nonatomic, strong) NSString * statement;
@property (nonatomic, assign) BOOL checked;

@end
