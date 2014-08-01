//
//  ListTableViewController.m
//  QCMPlus
//
//  Created by fitec on 31/07/2014.
//  Copyright (c) 2014 Guillaume Brunier. All rights reserved.
//

#import "ListTableViewController.h"
#import "MCQService.h"

@interface ListTableViewController ()
@property () NSArray * mcqs;
@end

@implementation ListTableViewController

- (void)viewDidLoad
{
    [super viewDidLoad];

    MCQService * service = [MCQService sharedInstance];
    [service fetchAvailableMCQs:^(BOOL success, NSArray *MCQs) {
        if (success) {
            [self displayMCQList:MCQs];
        }
    }];
}

- (void)displayMCQList:(NSArray *) mcqs
{
    self.mcqs = mcqs;
    [self.tableView reloadData];
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [[self mcqs] count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *simpleTableIdentifier = @"MCQ_TABLE_CELL";
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:simpleTableIdentifier];
    
    if (cell == nil) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:simpleTableIdentifier];
    }
    
    cell.textLabel.text = [[[self mcqs] objectAtIndex:indexPath.row] name];
    return cell;
}

@end
