//
//  MCQService.m
//  QCMPlus
//
//  Created by fitec on 29/07/2014.
//  Copyright (c) 2014 Guillaume Brunier. All rights reserved.
//

#import "MCQService.h"

#import "MCQDAO.h"
#import "UserDAO.h"
#import "UserMCQDAO.h"
#import "QuestionDAO.h"

@interface MCQService()
@property (nonatomic, strong) User * currentUser;
@end

@implementation MCQService

/*
 * Singleton
 */
+ (instancetype)sharedInstance
{
    static id __SharedInstance = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        __SharedInstance = [[self alloc] init];
    });
    return __SharedInstance;
}

/*
 * Connexion d'un utilisateur
 */
- (void)connectWithLogin:(NSString *)login
             andPassword:(NSString *)password
                callback:(OnLoginCompleted)callback;
{
    UserDAO * userDAO = [UserDAO sharedInstance];
    
    [userDAO performLoginWithUserName:login
                          andPassword:password
                             callback:^(BOOL success, User *user)
     {
         if(!success) {
             callback(FALSE);
         }
         else {
             self.currentUser = user;
             callback(TRUE);
         }
     }];

}

/*
 * Récupération de la liste des QCM réalisés
 */
- (void)fetchDoneMCQs:(OnDoneMCQsFetched)callback
{
    MCQDAO * dao = [MCQDAO sharedInstance];
    
    //Récupération de la liste des UserMCQ pour l'utilisateur actuel
    [dao findMCQsByUser:self.currentUser withState:@"DONE" callback:^(NSError *error, NSArray *mcqs) {
        if(!error) {
            callback(TRUE, mcqs);
        }
        else {
            callback(FALSE, nil);
        }

    }];
}

/*
 * Récupération de la liste des QCM disponibles
 */
- (void)fetchAvailableMCQs:(OnAvailableMCQsFetched)callback
{
    MCQDAO * dao = [MCQDAO sharedInstance];
    
    //Récupération de la liste des UserMCQ pour l'utilisateur actuel
    [dao findMCQsByUser:self.currentUser withState:@"TODO" callback:^(NSError *error, NSArray *mcqs) {
        if(!error) {
            callback(TRUE, mcqs);
        }
        else {
            callback(FALSE, nil);
        }
        
    }];
}

/*
 * Récupération des details d'un QCM
 */
- (void)fetchMCQDetails:(MCQ *)mcq
               callback:(OnMCQDetailsFetched)callback
{
    UserMCQDAO * dao = [UserMCQDAO sharedInstance];
    
    [dao findUserMCQByUser:self.currentUser andMCQ:mcq callback:^(NSError *error, UserMCQ *userMCQ) {
        if(!error) {
            callback(TRUE, userMCQ);
        }
        else {
            callback(FALSE, nil);
        }
    }];
}

/*
 * Récupération de la liste des questions detaillée d'un QCM
 */
- (void)fetchMCQQuestionsDetail:(MCQ *)mcq
                       callback:(OnMCQQuestionsDetailFetched)callback
{
    QuestionDAO * dao = [QuestionDAO sharedInstance];
    
    [dao findQuestionsByMCQ:mcq callback:^(NSError *error, NSArray *questions) {
        if(!error) {
            callback(TRUE, questions);
        }
        else {
            callback(FALSE, nil);
        }
    }];
}

@end
