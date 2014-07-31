//
//  LoginController.m
//  QCMPlus
//
//  Created by fitec on 23/07/2014.
//  Copyright (c) 2014 Guillaume Brunier. All rights reserved.
//

#import "LoginViewController.h"
#import "MCQService.h"

@interface LoginViewController () <UITextFieldDelegate>
@property (weak, nonatomic) IBOutlet UITextField *loginInput;
@property (weak, nonatomic) IBOutlet UITextField *passwordInput;
@end

@implementation LoginViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    self.passwordInput.delegate = self;
    self.loginInput.delegate = self;
}

- (IBAction)loginBtnClick:(id)sender
{
    NSString * login = [self.loginInput text];
    NSString * password = [self.passwordInput text];
    
    MCQService * service = [MCQService sharedInstance];
    
    [service connectWithLogin:login
                  andPassword:password
                     callback:^(BOOL success)
     {
         if(!success)
         {
             self.passwordInput.text = @"";
             
             UIAlertView * alert = [UIAlertView new];
             alert.title = @"Identifiant ou Mot de passe incorrect";
             alert.message = @"Veuillez recommencez";
             [alert addButtonWithTitle:@"OK"];
             [alert show];
         }
         else
         {
             [self performSegueWithIdentifier:@"LOGIN2HOME_SEGUE" sender:self];
         }
     }];
}

- (BOOL)textFieldShouldReturn:(UITextField *)textField
{
    [textField resignFirstResponder];
    return YES;
}

@end
