#import "FlutterKioskModePlugin.h"
#if __has_include(<flutter_kiosk_mode/flutter_kiosk_mode-Swift.h>)
#import <flutter_kiosk_mode/flutter_kiosk_mode-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "flutter_kiosk_mode-Swift.h"
#endif

@implementation FlutterKioskModePlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftFlutterKioskModePlugin registerWithRegistrar:registrar];
}
@end
