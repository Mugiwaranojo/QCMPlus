//
//  HomeTableViewController.m
//  QCMPlus
//
//  Created by fitec on 31/07/2014.
//  Copyright (c) 2014 Guillaume Brunier. All rights reserved.
//

#import "HomeTableViewController.h"
#import "DetailTableViewController.h"

#import "MCQService.h"

@interface HomeTableViewController ()
@property (nonatomic, strong) NSArray * mcqs;
@property (nonatomic, strong) MCQ * selectedMCQ;
@end

@implementation HomeTableViewController

- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    MCQService * service = [MCQService sharedInstance];
    [service fetchDoneMCQs:^(BOOL success, NSArray *MCQs) {
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
    return self.mcqs.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    //Notes : indexPath.section pour avoir la section. Voir xib
    
    static NSString *simpleTableIdentifier = @"MCQ_TABLE_CELL";
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:simpleTableIdentifier];
    
    if (cell == nil) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:simpleTableIdentifier];
    }
    
    cell.textLabel.text = [[self.mcqs objectAtIndex:indexPath.row] name];
    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    [self setSelectedMCQ:[self.mcqs objectAtIndex:indexPath.row]];
    
    [self performSegueWithIdentifier:@"HOME2DETAIL_SEGUE" sender:self];
}

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    if ([[segue identifier] isEqualToString:@"HOME2DETAIL_SEGUE"]) {
        
        DetailTableViewController *vc = [segue destinationViewController];
        
        [vc passCurrentMCQ:self.selectedMCQ];
    }
}


@end
