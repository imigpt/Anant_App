# Generate QR Code Screen - Visual Components Reference

## Screen Layout Structure

```
┌─────────────────────────────────────────┐
│         BLUE BACKGROUND (0xFF0066CC)   │
│                                         │
│  ┌──────┐                               │
│  │ ←    │ Back Button                   │
│  └──────┘                               │
│                                         │
│  ╔═════════════════════════════════════╗│
│  ║  GLASSMORPHIC CARD (60% opacity)   ║│
│  ║  ┌───────────────────────────────┐ ║│
│  ║  │  🔲 QR Code Icon              │ ║│
│  ║  │  Generate QR Code             │ ║│
│  ║  │  (Title, 24sp, Bold)          │ ║│
│  ║  └───────────────────────────────┘ ║│
│  ║                                     ║│
│  ║  Full Name                          ║│
│  ║  ┌─────────────────────────────┐   ║│
│  ║  │ [Input Field - 52dp height]│   ║│
│  ║  └─────────────────────────────┘   ║│
│  ║                                     ║│
│  ║  Upload Photo                       ║│
│  ║  ┌─────────────────────────────┐   ║│
│  ║  │  👤 (Profile Icon)          │   ║│
│  ║  │  Upload At least 5 Photos   │   ║│
│  ║  │  [Take Selfie] [Gallery]    │   ║│
│  ║  └─────────────────────────────┘   ║│
│  ║                                     ║│
│  ║  Vehicle Type                       ║│
│  ║  [2 Wheeler] [3 Wheeler] [.....]    ║│
│  ║  (Selectable chips)                 ║│
│  ║                                     ║│
│  ║  Vehicle Details                    ║│
│  ║  ┌─────────────────────────────┐   ║│
│  ║  │ Vehicle Number Plate        │   ║│
│  ║  └─────────────────────────────┘   ║│
│  ║  ┌─────────────────────────────┐   ║│
│  ║  │ Insurance Policy Number     │   ║│
│  ║  └─────────────────────────────┘   ║│
│  ║  ┌─────────────────────────────┐   ║│
│  ║  │ Insurance Valid Till        │   ║│
│  ║  └─────────────────────────────┘   ║│
│  ║                                     ║│
│  ║  Upload RC Copy (PDF/JPEG)          ║│
│  ║  ┌─────────────────────────────┐   ║│
│  ║  │  📄 (File Icon)             │   ║│
│  ║  │  Upload clear scan of RC    │   ║│
│  ║  │  [Upload Button]            │   ║│
│  ║  └─────────────────────────────┘   ║│
│  ║                                     ║│
│  ║  Upload Insurance Copy (PDF/JPEG)   ║│
│  ║  ┌─────────────────────────────┐   ║│
│  ║  │  📄 (File Icon)             │   ║│
│  ║  │  Add active insurance       │   ║│
│  ║  │  [Upload Button]            │   ║│
│  ║  └─────────────────────────────┘   ║│
│  ║                                     ║│
│  ║  Emergency Contact (Family)         ║│
│  ║  ┌─────────────────────────────┐   ║│
│  ║  │ Full Name                   │   ║│
│  ║  └─────────────────────────────┘   ║│
│  ║  ┌─────────────────────────────┐   ║│
│  ║  │ Enter Phone Number          │   ║│
│  ║  └─────────────────────────────┘   ║│
│  ║                                     ║│
│  ║  Emergency Contact (Friend)         ║│
│  ║  ┌─────────────────────────────┐   ║│
│  ║  │ Full Name                   │   ║│
│  ║  └─────────────────────────────┘   ║│
│  ║  ┌─────────────────────────────┐   ║│
│  ║  │ Enter Phone Number          │   ║│
│  ║  └─────────────────────────────┘   ║│
│  ║                                     ║│
│  ║  Any Medical Condition               ║│
│  ║  [Diabetes] [High BP] [Asthma]      ║│
│  ║  [Heart] [Epilepsy] [Allergies]     ║│
│  ║  [Organ] [Blood] [Pregnant] [Other] ║│
│  ║  (Selectable chips in flow)         ║│
│  ║                                     ║│
│  ║  This info will be shown if         ║│
│  ║  someone scans your QR in...        ║│
│  ║                                     ║│
│  ║  ┌─────────────────────────────┐   ║│
│  ║  │         NEXT BUTTON          │   ║│
│  ║  │  (White background, center)  │   ║│
│  ║  └─────────────────────────────┘   ║│
│  ║                                     ║│
│  ╚═════════════════════════════════════╝│
│                                         │
└─────────────────────────────────────────┘
```

## Component Dimensions

### Main Screen
- Width: Full width of device
- Background: Full height with blue gradient
- Safe area respected with padding

### Glassmorphic Card
- Width: 95% of screen width (5% padding on each side)
- Padding: 24dp internal
- Corner Radius: 24dp
- Background: Color.White with 60% alpha (0.6f)
- Elevation: 4dp shadow

### Input Fields
- Width: Fill max width of card
- Height: 52dp
- Padding: 16dp horizontal
- Corner Radius: 16dp
- Background: Color(0xFFE8F0FF) with 60% alpha
- Font Size: 14sp
- Placeholder Color: #999999 (gray)

### Buttons
- Height: 40-50dp depending on context
- Border Radius: 
  - Small buttons (photo/upload): 8dp
  - Chips: 24dp (pill-shaped)
  - Main button (Next): 12dp
- Elevation: 4dp

### Icons
- Small icons (header): 28dp
- Section icons: 40-48dp
- Profile icon: 48dp
- File icon: 40dp

### Text Styling

#### Titles
```
"Generate QR Code"
Font Size: 24sp
Font Weight: Bold
Color: #000000 (black)
Text Align: Center
```

#### Section Headers
```
"Upload Photo", "Vehicle Type", etc.
Font Size: 14sp
Font Weight: SemiBold
Color: #000000 (black)
```

#### Placeholder Text
```
"Full Name", "Vehicle Number Plate", etc.
Font Size: 14sp
Font Weight: Normal
Color: #999999 (gray)
```

#### Button Text
```
"Take Selfie", "Upload", "Next", etc.
Font Size: 12-16sp
Font Weight: SemiBold
Color: #000000 for white button
```

#### Footer Text
```
"This info will be shown if someone scans..."
Font Size: 11sp
Font Weight: Normal
Color: #666666 (dark gray)
Text Align: Center
```

## Color Palette

| Element | Color | Alpha | Hex Code |
|---------|-------|-------|----------|
| Main Background | Blue | 100% | #0066CC |
| Glass Card Background | White | 60% | #FFFFFF (α=0.6) |
| Input Field Background | Light Blue | 60% | #E8F0FF (α=0.6) |
| Selected Chip Background | Medium Blue | 100% | #4A90E2 |
| Unselected Chip Background | Light Blue | 50% | #E8F0FF (α=0.5) |
| Text Primary | Black | 100% | #000000 |
| Text Secondary | Gray | 100% | #666666 |
| Placeholder Text | Light Gray | 100% | #999999 |
| Icon Tint | Dark Gray | 100% | #6B7280 |

## Spacing Guide

| Section | Spacing |
|---------|---------|
| Between major sections | 28dp |
| Between inputs in a section | 12dp |
| Card padding | 24dp |
| Screen padding (top) | 16dp |
| Screen padding (bottom) | 24dp |
| Header back button area | 12dp |

## Component States

### Input Field States
```
1. Empty State:
   - Placeholder text visible
   - Light blue translucent background
   - Cursor at start

2. Focus State:
   - Keyboard visible
   - Text entered
   - Same background color maintained

3. Filled State:
   - User text visible
   - Light blue background
   - Maintains transparency
```

### Chip States
```
1. Unselected State:
   - Background: Light blue (50% opacity)
   - Text Color: Dark gray (#333333)
   - Clickable

2. Selected State:
   - Background: Medium blue (#4A90E2)
   - Text Color: Black (#000000)
   - Shows selection visually
```

### Button States
```
1. Idle State:
   - White background
   - Dark text
   - 4dp elevation

2. Pressed State:
   - Slightly darker background
   - Ripple effect (Material 3)

3. Upload Button (Before Upload):
   - White background
   - "Upload" text

4. Upload Button (After Upload):
   - Disabled or changed state (design dependent)
```

## Scrolling Behavior

- Main Column is scrollable vertically
- Horizontal scroll for vehicle type chips (LazyRow)
- FlowRow for medical conditions (wraps to next line)
- No horizontal scroll at screen level

## Responsive Design

### Phone (Small - 320dp width)
- Card width: 95% (maintains padding)
- Chips: Flow to multiple lines naturally
- Buttons: Stack if needed

### Tablet (Large - 800dp width)
- Card width: 95% but large logical width
- Chips: Fit more per row
- Buttons: Full width within card

### Landscape
- Height considerations apply
- Scroll may be needed more
- Layout remains the same

## Animation & Transitions

- No built-in animations (can be added)
- Focus transitions smooth via Material 3
- Chip selection toggles instantly
- Button press ripple effect (Material 3 default)

## Accessibility Features (Recommend Adding)

- Content descriptions on all icons
- Proper heading hierarchy
- Keyboard navigation support
- Touch target sizes (48x48dp minimum)
- Color contrast ratios > 4.5:1

## Visual Hierarchy

1. **Top Priority**: "Generate QR Code" title
2. **Primary Content**: Main form fields (name, vehicle, contacts)
3. **Secondary Content**: Upload sections
4. **Tertiary Content**: Medical conditions
5. **Footer**: Legal notice and Next button

## Dark Mode Considerations

Current implementation: Light theme only
- Background: Blue (#0066CC)
- Cards: White with opacity
- Text: Black/Dark Gray
- Could be adapted for dark mode by:
  - Using darker blue background
  - Using semi-transparent dark cards
  - Using lighter text colors

---

**Last Updated**: March 2026
**Design System**: Material 3
**Target Android Version**: API 24+
