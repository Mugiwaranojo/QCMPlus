//
//  DetailTableViewController.m
//  QCMPlus
//
//  Created by fitec on 31/07/2014.
//  Copyright (c) 2014 Guillaume Brunier. All rights reserved.
//

#import "DetailTableViewController.h"
#import "UserMCQ.h"
#import "MCQService.h"
#import "Question.h"

@interface DetailTableViewController ()
@property (weak, nonatomic) IBOutlet UILabel *mcqNameLabel;
@property (weak, nonatomic) IBOutlet UILabel *mcqScoreLabel;
@property (weak, nonatomic) IBOutlet UILabel *mcqDateLabel;
@property (weak, nonatomic) IBOutlet UILabel *mcqTimeSpentLabel;
@property () MCQ * currentMcq;
@property () NSArray * questions;
@end

@implementation DetailTableViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    MCQService * service = [MCQService sharedInstance];
    [service fetchMCQDetails:self.currentMcq callback:^(BOOL success, UserMCQ *userMCQ) {
        if (success){
            [self displayMCQDetails:userMCQ];
        }
    }];
    
    [service fetchMCQQuestionsDetail:self.currentMcq callback:^(BOOL success, NSArray *questions) {
        if (success){
            [self displayMCQQuestions:questions];
        }
    }];
}

- (void)displayMCQDetails:(UserMCQ *) userMCQ
{
    [self.mcqNameLabel setText:self.currentMcq.name];
    [self.mcqScoreLabel setText:[NSString stringWithFormat:@"%d", userMCQ.score]];
    
    int forHours = userMCQ.timeSpent / 3600;
    int remainder = userMCQ.timeSpent % 3600;
    int forMinutes = remainder / 60;
    int forSeconds = remainder % 60;
    
    [self.mcqTimeSpentLabel setText:[NSString stringWithFormat:@"%dh %dm %ds", forHours, forMinutes, forSeconds]];
    
    NSDateFormatter *timeFormatter = [[NSDateFormatter alloc] init];
    [timeFormatter setDateFormat:@"MM/dd/yyyy"];
    [timeFormatter setTimeZone:[NSTimeZone timeZoneWithName:@"GMT"]];
    NSString *dateUpdated = [timeFormatter stringFromDate:userMCQ.dateUpdated];
    
    [self.mcqDateLabel setText:dateUpdated];
}

- (void)displayMCQQuestions:(NSArray *) questions
{
    self.questions = questions;
    [self.tableView reloadData];
}

- (void)passCurrentMCQ:(MCQ *)mcq
{
    [self setCurrentMcq:mcq];
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return self.questions.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    //Notes : indexPath.section pour avoir la section. Voir xib
    
    static NSString *simpleTableIdentifier = @"MCQ_TABLE_CELL";
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:simpleTableIdentifier];
    
    if (cell == nil) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:simpleTableIdentifier];
    }
    
    cell.textLabel.text = [[self.questions objectAtIndex:indexPath.row] statement];
    return cell;
}

@end
