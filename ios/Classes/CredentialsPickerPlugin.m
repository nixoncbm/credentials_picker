#import "CredentialsPickerPlugin.h"
#if __has_include(<credentials_picker/credentials_picker-Swift.h>)
#import <credentials_picker/credentials_picker-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "credentials_picker-Swift.h"
#endif

@implementation CredentialsPickerPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftCredentialsPickerPlugin registerWithRegistrar:registrar];
}
@end
