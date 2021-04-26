//
//  FlowExtensions.swift
//  KaMPKitiOS
//
//  Created by kamil kalisz on 18/02/2021.
//  Copyright © 2021 Touchlab. All rights reserved.
//

import Foundation
import shared

extension Kotlinx_coroutines_coreMutableSharedFlow {
    func asCflow<T>() -> CommonFlow<T>{
        return FlowHelperKt.asCommonFlow(self) as! CommonFlow<T>
    }
    
    func asCCFlow<T>(on type:T.Type) -> CommonFlow<T>{
        return FlowHelperKt.asCommonFlow(self) as! CommonFlow<T>
    }
}
