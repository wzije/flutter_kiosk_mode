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

### “Checks whether the application is currently in Lock Task Mode (kiosk mode) or not.”

```dart
await _flutterKioskMode.check();
```

Example:
1. initState in the main screen

```dart
@override
void initState() {
  super.initState();
  _checkAndStartKiosk();
}

Future<void> _checkAndStartKiosk() async {
  final inKiosk = await channel.invokeMethod('check');
  if (!inKiosk) {
    await channel.invokeMethod('start');
  }
}

```

2. Press the activate kiosk mode button

```dart 
ElevatedButton(
    onPressed: () async {
        final inKiosk = await channel.invokeMethod('check');
        if (!inKiosk) {
          await channel.invokeMethod('start');
        } else {
          ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('Sudah dalam kiosk mode')),
          );
        }
    },
    child: const Text('Start Kiosk Mode'),
)
```

3. In the particular event
```dart
Navigator.push(context, MaterialPageRoute(builder: (_) => KioskPage()))
    .then((_) async {
            final inKiosk = await channel.invokeMethod('check');
            if (!inKiosk) {
                await channel.invokeMethod('start');
            }
});
 
```
