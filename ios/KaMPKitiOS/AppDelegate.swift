//
//  AppDelegate.swift
//  KaMPKitiOS
//
//  Created by Kevin Schildhorn on 12/18/19.
//  Copyright © 2019 Touchlab. All rights reserved.
//

import UIKit
import shared

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {
    
    var window: UIWindow?

    // Lazy so it doesn't try to initialize before startKoin() is called
    //lazy var log = koin.get(objCClass: Kermit.self, parameter: "AppDelegate") as! Kermit

    func application(_ application: UIApplication, didFinishLaunchingWithOptions
        launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        
        
        let koinApp = KoinMvvmKt.doInitKoinMvvm { (it) in
        
        }
        _koin = koinApp.koin
        //startKoin()
//        KoinKt.doInitKoin(appModule: T##Koin_coreModule)
//        startKoin {
//            ModuleKt.mo
//            modules(baseViewModelModule())
//        }
              
//         Manually launch storyboard so that ViewController doesn't initialize before Koin
        let storyboard = UIStoryboard(name: "LoginView", bundle: nil)
        let viewController = storyboard.instantiateViewController(identifier: "LoginViewController")
//
        self.window = UIWindow(frame: UIScreen.main.bounds)
        self.window?.rootViewController = viewController
        self.window?.makeKeyAndVisible()
        
        //log.v(withMessage: {"App Started"})
        return true
    }
}

