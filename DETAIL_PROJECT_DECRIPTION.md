
Perfect. Let me begin execution now.

---

# 🌿 Plant Care & Gardening Tracker
## Project Context & Functional Description Document

---

### 📋 PROJECT CONTEXT

The **Plant Care & Gardening Tracker** is a full-stack web application designed to serve both hobbyist gardeners and experienced horticulture enthusiasts. The system provides a centralized digital platform where users can manage their entire plant collection lifecycle — from the moment a plant is acquired, through its daily care routines, health monitoring, growth tracking, all the way to its retirement or gifting.

The platform solves a real-world problem: **plant owners forget watering schedules, miss fertilization windows, lose track of pest treatments, and have no historical record of plant growth.** This application brings structure, reminders, and intelligence to what is otherwise an informal and memory-dependent activity.

The system is built for **multi-device accessibility** (mobile, tablet, desktop), containerized for reliable deployment via Docker, and backed by a structured database ensuring data persistence. It serves individual users through secure account management, personalized care schedules, visual growth timelines, and a knowledge base — making it a comprehensive gardening companion.

---

---

## 🔐 SECTION 1 — USER MANAGEMENT

---

### 1.1 User Registration

**Description:**
User Registration is the entry point of the entire platform. It allows any new visitor to create a personal account using their email address and a password, establishing a unique identity within the system. This functionality is the foundation upon which all personalized features — plant catalogues, care schedules, reminders, and growth history — are built. Without registration, the system cannot associate any data with a specific individual, making this the most critical onboarding step.

**Flow:**
The user navigates to the registration page and is presented with a form requesting their email address and a password. Upon submission, the system validates the format of the email and ensures the password meets the required security criteria. The system then checks whether the email is already associated with an existing account. If the email is unique, the user's credentials are securely stored — with the password encrypted — and a new account is created. The user is then directed into the application as a registered member, ready to begin building their plant profile.

---

### 1.2 User Login

**Description:**
User Login is the authentication gateway that allows registered users to securely access their personal data and features within the platform. It ensures that only verified account holders can view or interact with their plant catalogue, care schedules, and history. This functionality protects user privacy and maintains the integrity of personal data stored in the system.

**Flow:**
The registered user visits the login page and enters their email and password. The system retrieves the stored account associated with that email and validates the entered password against the encrypted version on record. If the credentials match, the system creates a secure session for that user and grants access to their personalized dashboard. If the credentials are incorrect, the system notifies the user of the failure without revealing which specific field was wrong, maintaining security best practices. The user remains logged in through session management until they explicitly log out or the session expires.

---

### 1.3 Profile Management

**Description:**
Profile Management allows authenticated users to personalize their account by updating their gardening preferences and self-reported experience level. This functionality makes the platform adaptive — a beginner gardener and an expert horticulturist have different informational needs, and the system uses profile data to tailor the experience accordingly. It gives users ownership of their identity within the platform.

**Flow:**
Once logged in, the user navigates to their profile section. They are presented with editable fields covering their gardening preferences — such as plant types they grow or care about — and their experience level, which may range from beginner to expert. The user modifies the desired fields and submits the update. The system validates the input, persists the changes to the user's account record in the database, and confirms the successful update. These updated preferences are then reflected across the platform wherever personalization is applied.

---

### 1.4 Password Reset

**Description:**
Password Reset provides users with a secure self-service mechanism to regain access to their account when they have forgotten their password. This functionality is essential for user retention — without it, a forgotten password results in permanent account lockout. It operates entirely through the user's registered email, ensuring that only the legitimate account owner can initiate and complete the reset process.

**Flow:**
The user clicks the "Forgot Password" option on the login page and enters their registered email address. The system verifies that an account exists for that email. If found, the system generates a unique, time-limited password reset link and sends it to the provided email address. The user opens the email, clicks the link, and is directed to a secure page where they can enter and confirm a new password. The system validates the new password, updates the stored credentials, and invalidates the reset link so it cannot be reused. The user is then redirected to log in with their new password.

---

### 1.5 Session Management

**Description:**
Session Management is the underlying mechanism that keeps a user's authenticated state active and secure across their interaction with the platform. It ensures that once a user logs in, they do not need to re-authenticate for every action, while simultaneously protecting the account from unauthorized access. This functionality operates silently in the background but is foundational to a secure and seamless user experience.

**Flow:**
Upon successful login, the system creates a secure session token tied to the user's account and device context. This token is maintained throughout the user's active use of the application, allowing the system to recognize and authorize each subsequent request without requiring re-login. The system continuously monitors session validity — if the session remains idle beyond a defined threshold or the user explicitly logs out, the session is terminated and the token is invalidated. Any attempt to access protected areas with an expired or invalid session redirects the user back to the login page.

---

---

## 🌱 SECTION 2 — PLANT CATALOG & INVENTORY

---

### 2.1 Add Plant

**Description:**
Add Plant is the core data entry functionality that allows users to register a new plant into their personal digital catalogue. It captures the essential identity and acquisition details of a plant — its species, variety, and purchase information — forming the base record from which all care schedules, health logs, and growth tracking are linked. Every plant journey within the system begins here.

**Flow:**
The authenticated user navigates to the plant catalogue and selects the option to add a new plant. A form is presented requesting the plant's species, variety, and purchase-related details such as date of acquisition and source. The user fills in the available information and submits the form. The system validates the input for completeness and correctness, then creates a new plant record in the database linked to the user's account. The newly added plant appears in the user's catalogue, ready for further configuration of care schedules, location, and grouping.

---

### 2.2 Plant Details

**Description:**
Plant Details functionality enables the system to store and display comprehensive botanical and care-related information for each plant in the user's catalogue. This includes the plant's scientific name, common name, and specific care requirements. Having this data centralized ensures the user always has accurate, plant-specific reference information at their fingertips, reducing dependency on external sources for basic care guidance.

**Flow:**
When a plant is added or subsequently edited, the user can enter or update the scientific name, common name, and care requirements specific to that plant. The system stores this information against the plant's unique record. When the user views a specific plant from their catalogue, the system retrieves and displays all stored details in a structured, readable format. This information also serves as the reference base for generating care schedules and knowledge base associations tied to that plant.

---

### 2.3 Location Tracking

**Description:**
Location Tracking allows users to record and manage the physical placement of each plant within their living or gardening space. Whether a plant is indoors on a windowsill, outdoors in a garden bed, or in a specific room, knowing its location is essential for contextualizing care requirements such as light exposure, temperature sensitivity, and watering frequency. This functionality brings spatial awareness into the plant management experience.

**Flow:**
During the process of adding or editing a plant record, the user is presented with location fields where they can specify whether the plant is indoor or outdoor, and further narrow down the location to a specific room or garden bed identifier. The user selects or enters the appropriate location details and saves the record. The system stores the location against the plant entry. Users can later filter or browse their catalogue by location, making it easy to group and manage plants that share the same environmental conditions.

---

### 2.4 Plant Grouping

**Description:**
Plant Grouping allows users to organize their plants into named collections based on any logical grouping that is meaningful to them — such as "Herb Garden," "Succulents," "Balcony Plants," or "Indoor Collection." This functionality brings organizational structure to what can otherwise become a large and unmanageable catalogue, enabling users to manage and view related plants together efficiently.

**Flow:**
The user can create a named collection and assign one or more plants from their catalogue to it. This can be done either during the initial plant entry process or by editing existing plant records. The system stores the group association against each plant record. When the user browses their catalogue, they can filter the view by group, seeing only the plants belonging to a specific collection. A single plant may belong to multiple groups if applicable, and groups can be renamed or reorganized as the user's garden evolves.

---

### 2.5 Plant Status

**Description:**
Plant Status allows users to record and update the current lifecycle state of each plant in their catalogue. A plant's status — whether Active, Dormant, Gifted, or Deceased — reflects its real-world condition and determines how it is treated within the system. This functionality ensures the catalogue remains accurate and relevant, distinguishing plants that require active care from those that no longer do.

**Flow:**
Each plant record carries a status field that is set to "Active" by default upon creation. The user can update this status at any point by editing the plant record and selecting the appropriate status from a defined set of options. When a plant is marked as Dormant, the system acknowledges reduced care needs. When marked as Gifted or Deceased, the plant is moved out of the active care queue but retained in the catalogue for historical reference. The status is visually indicated in the catalogue view, allowing users to quickly distinguish the state of each plant at a glance.

---

---

## 📅 SECTION 3 — CARE SCHEDULE MANAGEMENT

---

### 3.1 Watering Schedule

**Description:**
Watering Schedule functionality enables users to define and track how frequently each plant in their collection needs to be watered, based on that plant's individual requirements. Overwatering and underwatering are among the most common causes of plant decline, making this one of the most critical features of the platform. The system acts as a reliable reminder engine ensuring no watering event is missed or duplicated unnecessarily.

**Flow:**
For each plant, the user navigates to the care schedule section and sets a watering frequency — for example, every two days or once a week. The system records this schedule against the plant's profile. Based on the defined frequency and the last recorded watering event, the system calculates the next due watering date and surfaces it in the upcoming tasks view. When the user logs a watering event, the system updates the last watered date and recalculates the next scheduled date automatically, maintaining a perpetually accurate watering timeline.

---

### 3.2 Fertilization Schedule

**Description:**
Fertilization Schedule allows users to plan and track when and how each plant should be fertilized. Fertilization is a time-sensitive activity that directly impacts plant health and growth — too little leads to nutrient deficiency, too much causes chemical burn. This functionality provides users with a structured plan so that fertilization is applied at the right intervals and properly recorded for future reference.

**Flow:**
The user accesses the care schedule for a specific plant and sets the fertilization frequency and any relevant details such as fertilizer type preference. The system stores this schedule and calculates upcoming fertilization dates based on the defined interval and the last logged application. When a fertilization event is due, it appears in the user's task list. Upon logging a completed fertilization, the system records the event and advances the next scheduled date, keeping the plan current and traceable over time.

---

### 3.3 Pruning/Trimming Schedule

**Description:**
Pruning and Trimming Schedule functionality allows users to schedule and track the maintenance cutting activities required to keep their plants healthy, shaped, and productive. Pruning at the right time promotes new growth and prevents disease, while irregular pruning can stress the plant. The system ensures users receive timely reminders and maintain a complete history of all pruning activities performed.

**Flow:**
The user sets a pruning frequency for a specific plant within the care schedule module. The system stores this alongside other care tasks and calculates the next due pruning date. When the pruning date approaches, it appears as an upcoming task. After the user performs and logs the pruning activity — including any relevant details — the system records the event with a timestamp and recalculates the next scheduled pruning date, ensuring the maintenance rhythm is preserved consistently.

---

### 3.4 Repotting Reminders

**Description:**
Repotting Reminders allow users to set advance notifications for when a plant is likely to need repotting — the process of transferring a plant to a larger container to support continued growth. Repotting at the right time is crucial; doing it too early disturbs a stable plant unnecessarily, while waiting too long restricts root growth and affects health. This functionality ensures the user is prompted at the right time based on their own defined schedule.

**Flow:**
The user configures a repotting reminder for a specific plant, setting either a specific future date or an interval from the last repotting event. The system stores this reminder and monitors the timeline. When the defined date approaches, the reminder surfaces in the user's upcoming tasks and notification feed. The user can then assess the plant's actual condition and either act on the reminder or defer and reset it. Once repotting is completed, the user logs the event and the system resets the reminder cycle accordingly.

---

### 3.5 Seasonal Adjustments

**Description:**
Seasonal Adjustments functionality enables the system to automatically adapt care schedules for plants based on the current season. Plant care requirements change significantly across seasons — watering frequency often decreases in winter, fertilization may pause during dormancy, and light requirements shift. This functionality reduces the burden on the user to manually reconfigure schedules as seasons change, keeping care plans aligned with natural growth cycles.

**Flow:**
The system tracks the current season based on the calendar. When a seasonal transition occurs, the system applies pre-defined seasonal adjustment rules to active care schedules across the user's plant catalogue. For example, watering intervals may be automatically extended during winter months. The user is informed of the adjustments made, with the option to review and override any automatic changes if their specific conditions differ. This ensures care schedules remain relevant and appropriately calibrated throughout the year without requiring constant manual intervention from the user.

---

---

## 📝 SECTION 4 — CARE ACTIVITY LOGGING

---

### 4.1 Log Watering

**Description:**
Log Watering allows users to record each watering event for a specific plant, capturing the date, amount of water used, and the method of watering applied. This creates a factual, timestamped history of watering activity that the system uses to calculate future schedule adherence and provides the user with an audit trail of care performed. It is the primary interaction users have with the system on a day-to-day basis.

**Flow:**
After watering a plant, the user navigates to that plant's activity log and selects the option to log a watering event. The system presents a simple entry form where the user confirms or enters the date, specifies the quantity of water applied, and selects the watering method used. Upon submission, the system records the event against the plant's history and uses the logged date to recalculate the next scheduled watering date within the care schedule. The entry becomes a permanent part of the plant's care history, visible in the activity timeline.

---

### 4.2 Log Fertilization

**Description:**
Log Fertilization enables users to record each fertilization event with specific details including the type of fertilizer used, the quantity applied, and the date of application. This level of detail is important for understanding what nutrients have been delivered to the plant over time, identifying patterns in plant response, and ensuring the correct fertilization products are reapplied consistently. It provides a precise chemical care history alongside the broader activity log.

**Flow:**
Following a fertilization activity, the user opens the plant's activity log and selects the fertilization logging option. The system presents a form where the user enters the fertilizer type, quantity applied, and application date. Upon saving, the system records this entry against the plant's history and updates the care schedule to reflect the completed task, advancing the next fertilization date. The logged entry is stored chronologically in the plant's activity history, contributing to a detailed nutritional care record over the plant's lifetime.

---

### 4.3 Log Pruning

**Description:**
Log Pruning allows users to record pruning and trimming activities performed on their plants, capturing specific details about the nature of the pruning and the techniques applied. Maintaining a pruning log helps users understand the plant's response to different pruning approaches over time, identify the best timing, and track how growth patterns evolve following each intervention. It transforms pruning from an ad-hoc task into a documented, informed practice.

**Flow:**
After completing a pruning activity, the user accesses the plant's activity log and selects the pruning entry option. The system presents fields for the user to describe the pruning details — such as the parts of the plant trimmed and the technique used — along with the date of the activity. The user submits the entry, and the system records it chronologically in the activity history. The associated care schedule is updated to reflect the completed task, and the next pruning date is recalculated based on the defined frequency.

---

### 4.4 Pest/Disease Tracking

**Description:**
Pest and Disease Tracking enables users to log any pest infestations or disease occurrences observed in their plants, along with a record of the treatments applied in response. Plant pests and diseases can spread rapidly and cause irreversible damage if not tracked and treated methodically. This functionality gives users a structured way to document problems as they arise, track the effectiveness of treatments, and build a reference history that informs future responses to similar issues.

**Flow:**
When a user observes signs of pest activity or disease on a plant, they navigate to that plant's log and create a pest or disease entry. The system allows the user to describe the observed symptoms, identify the pest or disease if known, and record the treatment applied along with the date. Subsequent entries can be added to track whether the treatment is working or if escalated action is needed. All entries are stored chronologically, creating a complete issue-and-response timeline that the user can reference whenever similar problems recur.

---

### 4.5 General Notes

**Description:**
General Notes provides users with a free-form text logging capability to record any observations, thoughts, or information about a plant that does not fit neatly into structured care categories. Plant care often involves nuanced observations — a slight colour change in leaves, an unusual growth direction, a change in the plant's environment — that are valuable to capture but do not belong to a specific event type. This functionality ensures nothing goes unrecorded simply because the system lacks a dedicated field for it.

**Flow:**
At any point, the user can navigate to a plant's activity section and add a general note. The system presents a free-text input field where the user types their observation or comment along with the current date. Upon saving, the note is stored chronologically in the plant's activity history alongside structured care log entries. Notes are retrievable through the plant's timeline view, allowing the user to scroll through a complete record of all interactions — structured and unstructured — in the order they occurred.

---

---

## 📈 SECTION 5 — GROWTH TRACKING

---

### 5.1 Photo Timeline

**Description:**
Photo Timeline allows users to upload photographs of their plants over time and organize them into a chronological visual record that illustrates the plant's growth journey. A picture-based timeline is one of the most compelling ways to observe plant progress — changes in size, leaf density, flowering, and overall health become strikingly visible when compared across time. This functionality transforms the plant's history into a living visual story.

**Flow:**
The user selects a plant from their catalogue and navigates to the photo timeline section. They upload a photograph — either from their device's gallery or captured directly via the camera — which the system associates with the plant and timestamps with the current date. The system displays all uploaded photos in chronological order, forming a scrollable visual timeline. Users can add a caption or note alongside each photo for additional context. Over time, the timeline grows into a complete photographic record of the plant's development from its earliest entry to the present day.

---

### 5.2 Measurement Logging

**Description:**
Measurement Logging allows users to record quantitative growth metrics for their plants — specifically height, width, and any other relevant physical dimensions — at regular intervals. Unlike photographs, numerical measurements provide precise, comparable data points that reveal growth trends over time in an objective and trackable manner. This functionality supports data-driven plant care by turning growth into a measurable, monitorable dimension.

**Flow:**
The user navigates to the growth tracking section of a specific plant and selects the measurement logging option. The system presents input fields for the relevant metrics — height, width, and any additional dimensions applicable to that plant type. The user enters the current measurements and the date of measurement. The system saves the entry and adds it to a chronological measurement log. These data points feed directly into the growth charts feature, where they are visualised graphically to reveal trends and milestones in the plant's physical development over time.

---

### 5.3 Bloom Tracking

**Description:**
Bloom Tracking enables users to log and monitor the flowering activity of their plants, recording when a plant begins blooming, how long the bloom period lasts, and how many blooms are present. For flowering plant owners, blooms represent one of the most anticipated and rewarding outcomes of successful care. Tracking bloom cycles helps users understand what conditions trigger flowering and how to replicate them in future growing seasons.

**Flow:**
When a plant begins to flower, the user navigates to the growth tracking section and logs the bloom event, recording the start date and initial bloom count. As the bloom period progresses, the user can update the count or add additional entries to capture changes. When blooming ends, the user logs the end date, completing the bloom period record. The system stores all bloom entries chronologically, building a history of flowering cycles for that plant. This history helps the user identify seasonal patterns and correlate bloom activity with specific care inputs recorded elsewhere in the system.

---

### 5.4 Harvest Tracking

**Description:**
Harvest Tracking is a growth logging functionality specifically designed for edible plants — vegetables, herbs, fruits, and similar produce-bearing species. It allows users to record each harvest event, capturing the date and the yield obtained. For users growing food plants, harvest tracking transforms gardening from a hobby into a measurable productive activity, enabling them to assess productivity across seasons and plan future growing cycles accordingly.

**Flow:**
When an edible plant produces a harvestable yield, the user navigates to that plant's growth tracking section and logs a harvest entry. The system presents fields for the harvest date and the yield quantity or description. Upon saving, the entry is added to the plant's harvest history chronologically. Over multiple growing seasons, these entries accumulate into a harvest record that reveals productivity trends, peak yield periods, and the impact of different care approaches on output. Users can review this history to make informed decisions about care and cultivation in future cycles.

---

### 5.5 Growth Charts

**Description:**
Growth Charts is the data visualization functionality that translates the numerical measurements logged over time into graphical representations, making growth trends immediately visible and understandable. Rather than reading through lists of numbers, users can see at a glance how their plant has grown — whether steadily, in spurts, or with plateaus — providing both satisfaction and actionable insight into the effectiveness of their care regimen.

**Flow:**
The system aggregates the measurement log entries recorded for a specific plant over time and renders them as visual charts within the growth tracking section. The charts display growth metrics — such as height and width — on a timeline axis, allowing users to observe trends, identify growth spurts, and note periods of stagnation. The charts update automatically as new measurement entries are logged. Users can interact with the chart to view specific data points, helping them correlate growth patterns with care events recorded in the activity log.

---

---

## 🔔 SECTION 6 — REMINDERS & NOTIFICATIONS

---

### 6.1 Upcoming Tasks

**Description:**
Upcoming Tasks is the central dashboard feature that aggregates all pending care activities across the user's entire plant collection and presents them in a prioritized, time-based view. It serves as the user's daily action list — a single place to see everything that needs attention today, tomorrow, and across the coming week. Without this consolidated view, users would need to check each plant's schedule individually, making it easy to miss critical care events.

**Flow:**
Upon logging into the platform, the system queries all active plant care schedules linked to the user's account and identifies tasks whose due dates fall within the current day, the following day, and the current week. These tasks are collected and displayed in a structured upcoming tasks panel on the user's dashboard, organised by urgency. The user can view which plant needs attention and what type of care is due, and can directly log the completed activity from the task view. As tasks are completed and logged, they are removed from the pending list and the next occurrence is scheduled automatically.

---

### 6.2 Custom Reminders

**Description:**
Custom Reminders allow users to configure plant-specific notification timing preferences, going beyond the default schedule-based alerts to set reminders at exact times that suit the user's daily routine. Different plants may require attention at different times of day, and different users have different schedules. This functionality puts the user in control of when they are reminded, making the notification system genuinely useful rather than disruptive.

**Flow:**
The user navigates to a specific plant's settings or the notifications configuration section and sets a preferred reminder time for one or more care activities associated with that plant. The system stores the custom timing preference linked to that plant and care type. When the scheduled care date arrives, the system triggers the reminder at the user-defined time rather than a generic system default. The user receives the notification at the configured moment, prompting them to attend to the specific plant and task. Custom reminder settings can be updated or removed at any time.

---

### 6.3 Weather Considerations

**Description:**
Weather Considerations functionality enables the system to suggest adjustments to the user's care activities based on seasonal weather patterns. Seasonal weather directly impacts how much water a plant requires, whether outdoor plants need protection, and how often fertilization should occur. By incorporating weather awareness, the system moves beyond rigid schedule management and provides contextually intelligent care guidance aligned with real environmental conditions.

**Flow:**
The system monitors the current season and associated general weather patterns relevant to the user's context. When conditions indicate a weather change likely to affect plant care — such as the onset of summer heat increasing water evaporation, or winter cold reducing growth — the system generates care suggestions informing the user to consider adjusting their watering frequency, moving sensitive plants indoors, or pausing fertilization during dormancy. These suggestions appear as contextual notifications or advisory messages within the platform. The user reviews the suggestion and decides whether to apply the recommended adjustment to their schedules.

---

### 6.4 Email Notifications

**Description:**
Email Notifications provide the system with the ability to reach users outside of the application itself, delivering reminders for overdue care tasks directly to the user's registered email address. This is critical for user engagement — not all users check the application daily, but most users check their email regularly. Email notifications act as a safety net, ensuring that critical care tasks are not missed even when the user has not opened the platform.

**Flow:**
The system monitors all active care schedules and tracks tasks that have passed their due date without a corresponding log entry. When a task is identified as overdue, the system composes a notification email detailing the plant name, the overdue care activity, and how long it has been outstanding. The email is sent to the user's registered address. The user receives the email, is reminded of the outstanding task, and can return to the platform to log the activity or check on the plant's status. The frequency and threshold for overdue notifications are governed by the system's notification rules.

---

### 6.5 Mobile Push Notifications

**Description:**
Mobile Push Notifications extend the platform's reminder capability to the user's mobile device, delivering real-time alerts for critical care tasks directly to their phone's notification tray even when the application is not open. Push notifications are the most immediate and effective channel for time-sensitive reminders, ensuring the user receives a direct prompt at the moment attention is needed. This functionality is presented as optional, respecting user preferences around notification volume and device permissions.

**Flow:**
The user opts into mobile push notifications through the application's settings, granting the necessary device permissions. The system registers the user's device for push delivery. When a critical care task — such as an urgent watering reminder or an overdue health check — is triggered, the system sends a push notification to the registered device. The notification appears on the user's phone with the plant name and required action. Tapping the notification opens the application directly to the relevant plant or task, allowing the user to act immediately. The user can opt out or configure push notification preferences at any time from within the settings.

---

---

## 🏥 SECTION 7 — PLANT HEALTH MONITORING

---

### 7.1 Health Indicators

**Description:**
Health Indicators provide a structured way for users to record and track the overall health status of each plant using a defined scale — Excellent, Good, Poor, or Critical. Having a standardized health rating system allows users to quickly assess the condition of their entire collection at a glance and prioritize attention toward plants in declining health. It transforms subjective observation into a trackable, comparable metric over time.

**Flow:**
The user navigates to a specific plant's health monitoring section and assigns a current health status from the available options. The system stores this rating with a timestamp, creating a health status history for that plant. Users can update the health indicator at any time as the plant's condition changes. The current health status is displayed prominently on the plant's catalogue card, giving the user an instant visual signal of which plants require attention. Over time, the history of health ratings reveals whether a plant is trending toward recovery or decline.

---

### 7.2 Symptom Logging

**Description:**
Symptom Logging allows users to record specific signs of plant stress, disease, or nutrient deficiency that they observe. Rather than simply marking a plant as unhealthy, this functionality captures the precise nature of the problem — yellowing leaves, wilting, spots, unusual discolouration, or stunted growth. This level of detail is essential for diagnosing the root cause of a health issue and selecting the appropriate treatment response.

**Flow:**
When the user observes concerning signs in a plant, they navigate to the health monitoring section and create a symptom log entry. The system provides fields to describe the symptoms observed, the date of observation, and the affected parts of the plant. Upon saving, the entry is recorded chronologically in the plant's health history. Successive symptom log entries allow the user to track whether symptoms are worsening, stabilizing, or improving over time — providing a clinical-style record that informs ongoing treatment decisions and contributes to the treatment history.

---

### 7.3 Treatment History

**Description:**
Treatment History maintains a complete, chronological record of every intervention applied to address a plant's health issues — covering remedies for pest infestations, disease treatments, and corrective care measures. This history is invaluable for understanding what has and has not worked for a specific plant, avoiding repetition of ineffective treatments, and building a knowledge base of successful interventions that can be referenced for future problems.

**Flow:**
Following any health-related intervention, the user logs a treatment entry in the health monitoring section. The system captures the treatment type, the product or method used, the quantity applied if relevant, and the date of application. The entry is stored against the plant's health record. Subsequent entries can note the observed effectiveness of the treatment, creating a feedback loop. The complete treatment history is viewable in chronological order, allowing the user to trace the full journey of a health issue from initial symptom identification through every treatment applied to the eventual outcome.

---

### 7.4 Environmental Factors

**Description:**
Environmental Factors logging allows users to record the ambient conditions surrounding a plant — specifically temperature, humidity, and light levels — at given points in time. Environmental conditions are among the most significant determinants of plant health, and understanding how they correlate with health changes, growth patterns, and disease occurrence provides users with powerful diagnostic insight. This functionality connects environmental context to the plant's overall health narrative.

**Flow:**
The user navigates to the environmental factors section of a specific plant and logs the current observed or measured conditions — entering temperature, humidity level, and an assessment of light exposure. The system timestamps the entry and stores it against the plant's record. Multiple entries over time build an environmental history that can be reviewed alongside the plant's health indicators and growth measurements. Users can use this data to identify correlations — for example, noticing that a plant's health declined during a period of low humidity — and use those insights to adjust the plant's environment proactively.

---

### 7.5 Recovery Tracking

**Description:**
Recovery Tracking allows users to monitor and document a plant's progress back to health following a period of illness, stress, or significant intervention. Recovery is rarely instantaneous, and tracking it provides users with evidence-based reassurance that their treatments are working — or an early warning that a different approach is needed. It closes the loop on the health monitoring cycle, connecting the initial problem identification through treatment to the eventual outcome.

**Flow:**
Once treatment has been applied to a distressed plant, the user begins logging recovery observations through the health monitoring section. Each entry records the current health status, any changes in symptoms, and relevant observations about the plant's response to treatment. The system stores these entries chronologically alongside the symptom and treatment logs, creating a unified health timeline. The user continues logging at regular intervals — updating the health indicator as the plant improves — until the plant returns to a satisfactory health status. The full recovery journey is preserved in the history, serving as a reference for managing similar situations in the future.

---

---

## 📚 SECTION 8 — GARDENING KNOWLEDGE BASE

---

### 8.1 Care Guides

**Description:**
Care Guides provide users with access to structured, plant-specific care instructions and tips within the platform. Rather than leaving users to search external sources for basic care information, the system offers an integrated reference that covers how to care for specific plant types. This functionality supports informed decision-making directly within the context of the user's own plant catalogue.

**Flow:**
The user navigates to the knowledge base section and searches for or browses care guides relevant to the plants in their collection. The system retrieves and displays the matching guide, presenting structured care instructions covering watering frequency, light requirements, soil preferences, fertilization guidance, and common care tips for that plant type. The user reads the guide and can apply the recommendations directly to their plant's care schedule configuration within the platform, creating a seamless link between reference information and actionable care planning.

---

### 8.2 Problem Solutions

**Description:**
Problem Solutions is a knowledge base module dedicated to helping users identify common plant problems and access recommended solutions. When a plant shows signs of trouble, the user needs fast, reliable guidance on what might be wrong and how to address it. This functionality provides that guidance within the platform, reducing the time between problem identification and effective intervention.

**Flow:**
The user accesses the problem solutions section of the knowledge base and searches for a symptom or problem type they have observed in their plant. The system retrieves relevant entries from the knowledge base and presents a structured description of the potential problem, its likely causes, and the recommended corrective actions. The user reviews the suggested solution and can immediately proceed to log a treatment or update their care schedule based on the guidance received. The integration between the knowledge base and the activity logging system ensures that solutions can be applied and tracked without leaving the platform.

---

### 8.3 Seasonal Tips

**Description:**
Seasonal Tips delivers gardening advice and care recommendations tailored to the current season, helping users understand what actions are most relevant and beneficial at different times of the year. Gardening is inherently seasonal, and the knowledge requirements of spring planting differ significantly from winter dormancy management. This functionality ensures users always have timely, contextually appropriate guidance available.

**Flow:**
The system determines the current season and surfaces relevant seasonal tips within the knowledge base or as highlighted content on the user's dashboard. The tips cover seasonal priorities such as preparing plants for winter, adjusting watering for summer heat, or initiating propagation in spring. The user reads the tips and decides which recommendations to act upon within their garden. The seasonal tips refresh automatically as the season transitions, ensuring the guidance presented always reflects what is most relevant to the user's current gardening context.

---

### 8.4 Plant Database

**Description:**
The Plant Database is a searchable repository of information covering a wide range of common houseplants and garden plants. It serves as the platform's internal encyclopaedia — allowing users to look up any plant by name and retrieve standardized information about that species. This supports users in identifying plants, understanding their characteristics before adding them to their collection, and accessing baseline care data for plants they are considering acquiring.

**Flow:**
The user navigates to the plant database within the knowledge base and enters a search query — either a common name or a scientific name. The system searches the database and returns matching entries, displaying relevant details for each result including the plant's common and scientific names, general care requirements, growth characteristics, and any notable traits. The user browses the results and selects the most relevant entry to view in full. If the user wishes to add the searched plant to their personal catalogue, the database record can serve as the starting reference for the new plant entry, pre-populating known fields.

---

### 8.5 User Tips

**Description:**
User Tips is a community-driven knowledge feature that allows users of the platform to contribute their own care experiences, insights, and practical advice for the benefit of other gardeners. Peer-sourced knowledge often contains practical, real-world nuance that formal care guides do not capture — specific techniques, regional adaptations, or trial-and-error lessons that only come from hands-on growing experience. This functionality enriches the knowledge base with lived experience from the platform's own community.

**Flow:**
A user who has discovered a useful technique or observation navigates to the user tips section and submits a tip, providing a description of their insight, the plant or situation it applies to, and any relevant context. The system stores the submission and makes it available for other users to browse and read. Other users can search or filter tips by plant type or topic and read community-contributed advice alongside the formal care guides. This creates a growing, community-enriched layer of practical knowledge that complements the structured content already available in the knowledge base.

---

---

## 📱 SECTION 9 — RESPONSIVE DESIGN

---

### 9.1 Mobile Compatibility

**Description:**
Mobile Compatibility ensures that the entire application is fully functional and visually coherent on smartphones with screen widths of 320 pixels and above. Given that users are most likely to log care activities, check schedules, and capture plant photos while physically present with their plants — which is a mobile context by nature — ensuring seamless mobile usability is not a luxury but a fundamental requirement of the platform.

**Flow:**
When a user accesses the application on a smartphone browser, the system detects the viewport dimensions and renders the interface using a responsive layout designed for narrow screens. Navigation, forms, buttons, and content areas all reflow and resize appropriately to fit within the available screen width. All core functionalities — including plant catalogue browsing, activity logging, care schedule management, and notification viewing — are fully accessible and operable without requiring horizontal scrolling or zooming. The experience is optimized for touch interaction on small screens throughout.

---

### 9.2 Tablet Compatibility

**Description:**
Tablet Compatibility ensures the application adapts appropriately for medium-sized screens with widths from 768 pixels upward. Tablets represent a middle ground between mobile and desktop experiences, and the interface should take advantage of the additional screen real estate to present more information simultaneously without overwhelming the user. Users who prefer managing their plant catalogue in a more comfortable, spacious format benefit from a tablet-optimized layout.

**Flow:**
When the application is accessed on a tablet device, the responsive layout system detects the intermediate viewport size and renders an adjusted interface that takes advantage of the wider screen — for example, displaying plant catalogue entries in a multi-column grid or presenting care schedule details alongside the plant list in a split view. All interactive elements remain comfortably sized for touch use, and navigation remains intuitive. The experience is visually richer than the mobile view while remaining simpler and more touch-friendly than the full desktop layout.

---

### 9.3 Desktop Compatibility

**Description:**
Desktop Compatibility ensures the application delivers a full-featured, visually polished experience on screens with widths of 1024 pixels and above. Desktop users typically engage in more detailed tasks — reviewing growth charts, configuring care schedules, browsing the knowledge base — and expect a layout that presents comprehensive information efficiently. The desktop experience is the most feature-rich rendering of the platform.

**Flow:**
When accessed via a desktop browser, the application renders its full layout — utilizing the available screen width to display multi-panel views, detailed data tables, expanded navigation menus, and rich visualizations such as growth charts. All functionality is accessible via mouse and keyboard interaction. The interface presents maximum information density appropriate to the screen size, reducing the need for navigational depth and keeping key data visible without excessive scrolling. The desktop layout is designed for productivity and detailed plant management sessions.

---

### 9.4 Touch Interactions

**Description:**
Touch Interactions ensures that all interactive elements within the application — buttons, form fields, navigation items, toggles, and action controls — are designed and sized to be comfortably and accurately operable via touch input on mobile and tablet devices. Poorly sized touch targets are one of the most common sources of frustration in mobile applications, and this functionality guarantees that the platform is genuinely ergonomic on touch-based devices.

**Flow:**
Throughout the application's mobile and tablet interfaces, all interactive elements are rendered with touch-friendly sizing — meeting minimum tap target dimensions to prevent accidental activation of adjacent controls. Gestures such as scrolling, swiping, and tapping are handled responsively with no perceptible lag. Forms are designed so that activating an input field brings up the appropriate mobile keyboard type — numeric for measurements, email type for email fields — without obscuring critical content. The overall interaction model is built around touch-first conventions so that the experience feels native and intuitive on touchscreen devices.

---

### 9.5 Photo Capture

**Description:**
Photo Capture functionality enables users to upload photographs directly from their mobile device's camera or gallery with minimal friction, making it easy to document plant appearance and growth from wherever they are in their garden or home. The ability to capture and upload photos in the moment — rather than transferring files later from a computer — is essential to making the photo timeline feature practically usable on mobile devices.

**Flow:**
When the user chooses to add a photo to a plant's timeline from a mobile device, the system presents options to either open the device's camera for an immediate capture or select an existing image from the device gallery. The user captures or selects the photo, which is then uploaded to the system and associated with the relevant plant record, timestamped with the current date. The image appears immediately in the plant's photo timeline. The upload process is optimised for mobile network conditions, and the interface provides clear feedback on upload progress and successful completion.

---

---

## 🐳 SECTION 10 — DOCKER & DEPLOYMENT

---

### 10.1 Docker Container

**Description:**
Docker Container support ensures that the entire application is packaged into a self-contained, portable container image that can be run consistently across any environment that supports Docker — whether a developer's local machine, a staging server, or a production host. Containerization eliminates the "works on my machine" problem by bundling the application with all its dependencies, runtime, and configuration into a single deployable unit.

**Flow:**
The application is packaged using a Dockerfile that defines the base runtime environment, copies the application code, installs required dependencies, and specifies the startup command. When the Docker image is built, it produces a fully self-contained unit ready for deployment. An operator or developer runs the container using a standard Docker command, and the application starts within the container, listening on the configured port. The container is isolated from the host system, ensuring no dependency conflicts, and can be stopped, restarted, or replaced without affecting the underlying infrastructure.

---

### 10.2 Docker Compose

**Description:**
Docker Compose provides a multi-container orchestration configuration that defines and manages the application container alongside its dependent services — primarily the database — as a coordinated unit. In a real deployment, an application rarely runs in isolation; it requires a database server, and potentially other services, to function. Docker Compose allows all of these to be started, stopped, and managed together with a single command, simplifying both development setup and deployment operations.

**Flow:**
The Docker Compose configuration file defines the application service and the database service, specifying how they relate to each other — including the network through which they communicate and the dependency order in which they start. An operator executes the Compose startup command, which pulls or builds the required container images, creates the defined network, starts the database container first, and then starts the application container once the database is ready. The application connects to the database through the defined internal network. Both services can be stopped together with a single command, and the entire environment can be reproduced identically on any machine with Docker installed.

---

### 10.3 Environment Configuration

**Description:**
Environment Configuration ensures that the application's runtime behaviour — including database connection details, security keys, and feature settings — is controlled through environment variables rather than hardcoded values within the application code. This separation of configuration from code is a foundational best practice that enables the same application image to be deployed across different environments — development, testing, and production — simply by supplying different variable values, without modifying or rebuilding the application itself.

**Flow:**
The application is written to read all environment-specific values — such as database host, port, credentials, application secret keys, and any configurable feature flags — from environment variables at startup. These variables are supplied externally, either through a Docker Compose environment definition, a `.env` file, or the hosting platform's secret management system. When the container starts, it reads these values and configures itself accordingly. Changing a configuration value requires only updating the environment variable and restarting the container — no code changes, no rebuilding — making environment management clean, auditable, and secure.

---

### 10.4 Database Persistence

**Description:**
Database Persistence ensures that all data stored by the application — user accounts, plant records, care logs, health history, and growth data — survives container restarts, upgrades, and redeployments without loss. Containers are by nature ephemeral; without explicit persistence configuration, any data written inside a container is lost when that container stops. This functionality guarantees that the application's data layer is durable and reliable regardless of what happens to the container itself.

**Flow:**
The Docker configuration maps the database container's data storage directory to a persistent volume on the host system. This means that all data written by the database engine is stored on the host's filesystem rather than inside the container's ephemeral layer. When the database container stops or is replaced — such as during an application update or server restart — the volume remains intact on the host. When the new container starts, it mounts the same volume and picks up exactly where the previous instance left off, with all data fully preserved. Users experience no data loss through normal operational events such as restarts or upgrades.

---

### 10.5 Production Readiness

**Description:**
Production Readiness encompasses the security and configuration hardening applied to the application before it is deployed to a live environment serving real users. A development configuration prioritizes convenience; a production configuration prioritizes security, stability, and reliability. This functionality ensures that the deployed application is not exposing sensitive information, using insecure defaults, or operating in a mode that is appropriate only for testing — making it safe and suitable for real-world use.

**Flow:**
The production configuration applies a defined set of hardening measures: secret keys and credentials are supplied exclusively via secure environment variables and never embedded in code or configuration files; debug modes and verbose error outputs are disabled so that internal system details are not exposed to end users; HTTPS is enforced for all communication; database access is restricted to the application's internal network; and sensitive configuration files are excluded from the container image. When deployed in this configuration, the application behaves in a manner that protects user data, resists common attack vectors, and operates stably under the conditions of a live production environment.

---

---

## 🧪 SECTION 11 — TESTING & DOCUMENTATION

---

### 11.1 Unit Tests

**Description:**
Unit Tests provide automated verification of the application's core business logic at the most granular level — testing individual functions, methods, and components in isolation to confirm they produce the correct output for a given input. Unit tests are the first line of defence against bugs, catching logical errors at the point of development rather than after deployment. They also serve as living documentation of what each piece of logic is expected to do.

**Flow:**
Developers write unit tests for all critical business logic functions — such as care schedule calculation, health status evaluation, and data validation rules. Each test defines a specific input scenario and asserts the expected output. The tests are executed automatically as part of the development workflow. When a test fails, it immediately identifies the specific function and condition that is not behaving as expected, allowing the developer to locate and fix the issue precisely. The unit test suite runs continuously to ensure that new code changes do not break existing verified behaviour.

---

### 11.2 Integration Tests

**Description:**
Integration Tests verify that the application's components work correctly together — specifically that API endpoints interact properly with the database, that data flows correctly from the interface layer through the business logic to persistent storage, and that the integrated system behaves as expected end-to-end. While unit tests verify individual pieces, integration tests verify that those pieces connect and cooperate correctly.

**Flow:**
Integration tests are written to exercise the application's API endpoints with realistic request payloads, simulating the actions a real user or client would perform. Each test sends a request to an endpoint, verifies that the correct response is returned, and confirms that the expected changes have been made in the database. Tests cover both success scenarios and error conditions — for example, verifying that an invalid plant entry is rejected with the appropriate error message and that no partial data is written to the database. These tests are executed against a controlled test environment that mirrors the production database structure.

---

### 11.3 UI Tests

**Description:**
UI Tests provide automated verification of critical user flows through the application's actual interface — simulating real user interactions such as registering an account, adding a plant, logging a care activity, and viewing growth charts. While unit and integration tests verify the backend logic and data layer, UI tests validate that the complete system delivers the correct experience to the user in a browser environment.

**Flow:**
Automated UI tests are scripted to replicate key user journeys through the application — navigating to pages, filling in forms, clicking buttons, and verifying that the expected outcomes appear on screen. The tests run in a controlled browser environment and step through each action programmatically. If any step in a critical flow produces an unexpected result — a missing element, an incorrect message, or a navigation failure — the test fails and reports precisely where the breakdown occurred. UI tests are executed as part of the release validation process to confirm the application is functionally correct from the user's perspective before deployment.

---

### 11.4 API Documentation

**Description:**
API Documentation provides a structured, standardized reference for all REST API endpoints exposed by the application, generated using OpenAPI/Swagger specification. This documentation is essential for any developer integrating with, testing, or extending the platform — it defines what endpoints exist, what inputs they accept, what responses they return, and what error conditions they handle. It transforms an opaque system into a transparent, understandable interface.

**Flow:**
The application is instrumented with OpenAPI/Swagger annotations or configuration that describe each API endpoint — its path, HTTP method, accepted request parameters and body structure, and all possible response codes and formats. The Swagger tooling processes these definitions and generates an interactive documentation interface accessible via a browser. Developers navigating to the documentation URL see a complete list of all endpoints, can read detailed descriptions of each, and can execute test requests directly from the documentation interface. The documentation updates automatically as the API evolves, ensuring it remains accurate and current.

---

### 11.5 User Guide

**Description:**
The User Guide is a comprehensive documentation resource written for end users of the platform — covering how to use all features of the application from account registration through plant management, care scheduling, health monitoring, and growth tracking. It serves as the primary self-service support resource, enabling users to understand and use the platform effectively without requiring direct assistance. A well-written user guide reduces support overhead and increases user confidence and adoption.

**Flow:**
The user guide is structured around the platform's key functional areas, with each section explaining the purpose of the feature, the steps required to use it, and any relevant tips or considerations. Users access the guide either through a dedicated help section within the application or as a standalone document. When a user encounters an unfamiliar feature or an unexpected situation, they consult the guide to find the relevant section, read the explanation and steps, and return to the application to proceed with confidence. The guide is maintained alongside the application, with updates made whenever new features are introduced or existing flows change.

---

### 11.6 Code Comments

**Description:**
Code Comments refers to the practice of embedding meaningful, explanatory annotations directly within the application's source code. Comments explain the intent behind non-obvious logic, document the purpose of functions and modules, and provide context that the code itself cannot convey. For a codebase that will be maintained, extended, or reviewed by multiple developers over time, well-placed comments are the difference between a readable, maintainable system and an opaque one that is difficult to modify safely.

**Flow:**
Throughout the development of the application, developers add comments at appropriate points in the source code — at the beginning of functions to describe their purpose and parameters, inline within complex logic to explain non-obvious decisions, and at the module level to describe the overall responsibility of that component. Comments are reviewed as part of the code review process to ensure they are accurate, meaningful, and not merely restating what the code already makes obvious. When the codebase is read by a new developer or revisited after a period of time, the comments provide the necessary context to understand, navigate, and safely modify the code without risk of unintended consequences.

---

---

### 📊 DOCUMENT SUMMARY

| Section | Total KPIs | All Pass |
|---|---|---|
| User Management | 5 | ✅ |
| Plant Catalog & Inventory | 5 | ✅ |
| Care Schedule Management | 5 | ✅ |
| Care Activity Logging | 5 | ✅ |
| Growth Tracking | 5 | ✅ |
| Reminders & Notifications | 5 | ✅ |
| Plant Health Monitoring | 5 | ✅ |
| Gardening Knowledge Base | 5 | ✅ |
| Responsive Design | 5 | ✅ |
| Docker & Deployment | 5 | ✅ |
| Testing & Documentation | 6 | ✅ |
| **TOTAL** | **56** | **56/56 ✅** |

---

> **Document prepared strictly from provided KPI data. Zero assumptions made beyond the scope of the attached document.**