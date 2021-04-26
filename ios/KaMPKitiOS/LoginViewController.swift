//
//  LoginViewController.swift
//  KaMPKitiOS
//
//  Created by kamil kalisz on 18/02/2021.
//  Copyright © 2021 Touchlab. All rights reserved.
//

import Foundation
import UIKit
import shared


class LoginViewController: UIViewController {
    
    @IBOutlet weak var login: UITextField!
    @IBOutlet weak var password: UITextField!
    @IBOutlet weak var loginButton: UIButton!
    @IBOutlet weak var progress: UIActivityIndicatorView!
    
    lazy var viewModel: LoginViewModel = koin.get(objCClass: LoginViewModel.self, qualifier: nil) as! LoginViewModel
    
    required init?(coder: NSCoder) {
        super.init(coder: coder)
//        FlowHelperKt.asCommonFlow(viewModel.state).watch(block: { value in
//            //value.
//        })
        
        //iewModel.state.
        //viewModel.state.asCommonFlow().wa
//        viewModel.state.asCflow<LoginState>().watch { ups in
//
//        }
        
        var stringValue = viewModel.newState[0]
        
        stringValue.
        
        
        viewModel.state.asCCFlow(on: LoginState.self).watch { [weak self] ups in
            guard let state = ups else {
                return
            }
            if(state.isProgress){
                self?.progress.startAnimating()
            }else{
                self?.progress.stopAnimating()
            }
            //print(ups?.isError)
        }
    }
    
    @IBAction func onLoginClicked(_ sender: Any) {
        viewModel.login(username: "", password: "")
    }
}
