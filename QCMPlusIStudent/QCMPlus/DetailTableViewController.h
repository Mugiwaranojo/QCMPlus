//
//  DetailTableViewController.h
//  QCMPlus
//
//  Created by fitec on 31/07/2014.
//  Copyright (c) 2014 Guillaume Brunier. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "MCQ.h"

@interface DetailTableViewController : UITableViewController
- (void) passCurrentMCQ:(MCQ *)mcq;
@end
