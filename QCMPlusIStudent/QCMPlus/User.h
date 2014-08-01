//
//  User.h
//  QCMPlus
//
//  Created by fitec on 23/07/2014.
//  Copyright (c) 2014 Guillaume Brunier. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface User : NSObject

@property (nonatomic, strong) NSString * objectId;
@property (nonatomic, strong) NSString * firstname;
@property (nonatomic, strong) NSString * lastname;
@property (nonatomic, strong) NSString * compagny;
@property (nonatomic, strong) NSString * login;
@property (nonatomic, strong) NSString * password;
@property (nonatomic, assign) BOOL isAdmin;
@property (nonatomic, strong) NSArray * userMCQs;

@end