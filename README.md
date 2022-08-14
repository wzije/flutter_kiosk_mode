# flutter_kiosk_mode

Kiosk for Android

## Usage

- pubspec.yml

```yaml
dependencies:
  flutter_kiosk_mode: ^0.0.1
```

### New Instance

```dart
 final _flutterKioskMode = FlutterKioskMode.instance();
```

### Start Kiosk

```dart
await _flutterKioskMode.start();
```

### Stop Kiosk

```dart
await _flutterKioskMode.stop();
```

### Check Owner Kiosk

```dart
await _flutterKioskMode.owner();
```