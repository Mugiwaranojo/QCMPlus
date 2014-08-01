//
//  UserAnswer.h
//  QCMPlus
//
//  Created by fitec on 28/07/2014.
//  Copyright (c) 2014 Guillaume Brunier. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "Option.h"

@interface UserAnswer : NSObject

@property (nonatomic, strong) NSString * objectId;
@property (nonatomic, strong) Option * option;

@end
