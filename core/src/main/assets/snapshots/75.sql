CREATE TABLE Configuration (_id INTEGER PRIMARY KEY AUTOINCREMENT, serverUrl TEXT NOT NULL UNIQUE);
CREATE TABLE User (_id INTEGER PRIMARY KEY AUTOINCREMENT, uid TEXT NOT NULL UNIQUE, code TEXT, name TEXT, displayName TEXT, created TEXT, lastUpdated TEXT, birthday TEXT, education TEXT, gender TEXT, jobTitle TEXT, surname TEXT, firstName TEXT, introduction TEXT, employer TEXT, interests TEXT, languages TEXT, email TEXT, phoneNumber TEXT, nationality TEXT);
CREATE TABLE UserCredentials (_id INTEGER PRIMARY KEY AUTOINCREMENT, uid TEXT NOT NULL UNIQUE, code TEXT, name TEXT, displayName TEXT, created TEXT, lastUpdated TEXT, username TEXT, user TEXT NOT NULL UNIQUE, FOREIGN KEY (user) REFERENCES User (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED);
CREATE TABLE OrganisationUnit (_id INTEGER PRIMARY KEY AUTOINCREMENT, uid TEXT NOT NULL UNIQUE, code TEXT, name TEXT, displayName TEXT, created TEXT, lastUpdated TEXT, shortName TEXT, displayShortName TEXT, description TEXT, displayDescription TEXT, path TEXT, openingDate TEXT, closedDate TEXT, level INTEGER, parent TEXT, displayNamePath TEXT, geometryType TEXT, geometryCoordinates TEXT);
CREATE TABLE OptionSet (_id INTEGER PRIMARY KEY AUTOINCREMENT, uid TEXT NOT NULL UNIQUE, code TEXT, name TEXT, displayName TEXT, created TEXT, lastUpdated TEXT, version INTEGER, valueType TEXT);
CREATE TABLE Option (_id INTEGER PRIMARY KEY AUTOINCREMENT, uid TEXT NOT NULL UNIQUE, code TEXT, name TEXT, displayName TEXT, created TEXT, lastUpdated TEXT, optionSet TEXT NOT NULL, sortOrder INTEGER, color TEXT, icon TEXT, FOREIGN KEY (optionSet) REFERENCES OptionSet (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED);
CREATE INDEX optionset_optioncode ON Option(optionSet, code);
CREATE TABLE TrackedEntityType (_id INTEGER PRIMARY KEY AUTOINCREMENT, uid TEXT NOT NULL UNIQUE, code TEXT, name TEXT, displayName TEXT, created TEXT, lastUpdated TEXT, shortName TEXT, displayShortName TEXT, description TEXT, displayDescription TEXT, featureType TEXT, color TEXT, icon TEXT);
CREATE TABLE ProgramStageSection (_id INTEGER PRIMARY KEY AUTOINCREMENT, uid TEXT NOT NULL UNIQUE, code TEXT, name TEXT, displayName TEXT, created TEXT, lastUpdated TEXT, sortOrder INTEGER, programStage TEXT NOT NULL, desktopRenderType TEXT, mobileRenderType TEXT, FOREIGN KEY (programStage) REFERENCES ProgramStage (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED);
CREATE TABLE ProgramRuleVariable (_id INTEGER PRIMARY KEY AUTOINCREMENT, uid TEXT NOT NULL UNIQUE, code TEXT, name TEXT, displayName TEXT, created TEXT, lastUpdated TEXT, useCodeForOptionSet INTEGER, program TEXT NOT NULL, programStage TEXT, dataElement TEXT, trackedEntityAttribute TEXT, programRuleVariableSourceType TEXT, FOREIGN KEY (program) REFERENCES Program (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (programStage) REFERENCES ProgramStage (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (trackedEntityAttribute) REFERENCES TrackedEntityAttribute (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (dataElement) REFERENCES DataElement (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED);
CREATE TABLE ProgramTrackedEntityAttribute (_id INTEGER PRIMARY KEY AUTOINCREMENT, uid TEXT NOT NULL UNIQUE, code TEXT, name TEXT, displayName TEXT, created TEXT, lastUpdated TEXT, shortName TEXT, displayShortName TEXT, description TEXT, displayDescription TEXT, mandatory INTEGER, trackedEntityAttribute TEXT NOT NULL, allowFutureDate INTEGER, displayInList INTEGER, program TEXT NOT NULL, sortOrder INTEGER, searchable INTEGER, FOREIGN KEY (trackedEntityAttribute) REFERENCES TrackedEntityAttribute (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (program) REFERENCES Program (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED);
CREATE TABLE Constant (_id INTEGER PRIMARY KEY AUTOINCREMENT, uid TEXT NOT NULL UNIQUE, code TEXT, name TEXT, displayName TEXT, created TEXT, lastUpdated TEXT, value TEXT);
CREATE TABLE SystemInfo (_id INTEGER PRIMARY KEY AUTOINCREMENT, serverDate TEXT, dateFormat TEXT, version TEXT, contextPath TEXT, systemName TEXT);
CREATE TABLE ProgramRule (_id INTEGER PRIMARY KEY AUTOINCREMENT, uid TEXT NOT NULL UNIQUE, code TEXT, name TEXT, displayName TEXT, created TEXT, lastUpdated TEXT, priority INTEGER, condition TEXT, program TEXT NOT NULL, programStage TEXT, FOREIGN KEY (program) REFERENCES Program (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (programStage) REFERENCES ProgramStage (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED);
CREATE TABLE ProgramIndicator (_id INTEGER PRIMARY KEY AUTOINCREMENT, uid TEXT NOT NULL UNIQUE, code TEXT, name TEXT, displayName TEXT, created TEXT, lastUpdated TEXT, shortName TEXT, displayShortName TEXT, description TEXT, displayDescription TEXT, displayInForm INTEGER, expression TEXT, dimensionItem TEXT, filter TEXT, decimals INTEGER, program TEXT NOT NULL, aggregationType TEXT, FOREIGN KEY (program) REFERENCES Program (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED);
CREATE TABLE Resource (_id INTEGER PRIMARY KEY AUTOINCREMENT, resourceType TEXT NOT NULL, lastSynced TEXT);
CREATE TABLE OrganisationUnitProgramLink (_id INTEGER PRIMARY KEY AUTOINCREMENT, organisationUnit TEXT NOT NULL, program TEXT NOT NULL, FOREIGN KEY (organisationUnit) REFERENCES OrganisationUnit (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (program) REFERENCES Program (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, UNIQUE (organisationUnit, program));
CREATE TABLE UserRole (_id INTEGER PRIMARY KEY AUTOINCREMENT, uid TEXT NOT NULL UNIQUE, code TEXT, name TEXT, displayName TEXT, created TEXT, lastUpdated TEXT);
CREATE TABLE ProgramStageSectionProgramIndicatorLink (_id INTEGER PRIMARY KEY AUTOINCREMENT, programStageSection TEXT NOT NULL, programIndicator TEXT NOT NULL, FOREIGN KEY (programStageSection) REFERENCES ProgramStageSection (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (programIndicator) REFERENCES ProgramIndicator (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, UNIQUE (programStageSection, programIndicator));
CREATE TABLE Category (_id INTEGER PRIMARY KEY AUTOINCREMENT, uid TEXT NOT NULL UNIQUE, code TEXT, name TEXT, displayName TEXT, created TEXT, lastUpdated TEXT, dataDimensionType TEXT);
CREATE TABLE CategoryOption (_id INTEGER PRIMARY KEY AUTOINCREMENT, uid TEXT NOT NULL UNIQUE, code TEXT, name TEXT, displayName TEXT, created TEXT, lastUpdated TEXT, shortName TEXT, displayShortName TEXT, description TEXT, displayDescription TEXT, startDate TEXT, endDate TEXT, accessDataWrite INTEGER);
CREATE TABLE CategoryCategoryOptionLink (_id INTEGER PRIMARY KEY AUTOINCREMENT, category TEXT NOT NULL, categoryOption TEXT NOT NULL, sortOrder INTEGER, FOREIGN KEY (category) REFERENCES Category (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (categoryOption) REFERENCES CategoryOption (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, UNIQUE (category, categoryOption));
CREATE TABLE CategoryCombo (_id INTEGER PRIMARY KEY AUTOINCREMENT, uid TEXT NOT NULL UNIQUE, code TEXT, name TEXT, displayName TEXT, created TEXT, lastUpdated TEXT, isDefault INTEGER);
CREATE TABLE CategoryCategoryComboLink (_id INTEGER PRIMARY KEY AUTOINCREMENT, category TEXT NOT NULL, categoryCombo TEXT NOT NULL, sortOrder INTEGER, FOREIGN KEY (category) REFERENCES Category (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (categoryCombo) REFERENCES CategoryCombo (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, UNIQUE (category, categoryCombo));
CREATE TABLE CategoryOptionCombo (_id INTEGER PRIMARY KEY AUTOINCREMENT, uid TEXT NOT NULL UNIQUE, code TEXT, name TEXT, displayName TEXT, created TEXT, lastUpdated TEXT, categoryCombo TEXT, FOREIGN KEY (categoryCombo) REFERENCES CategoryCombo (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED);
CREATE TABLE DataSet (_id INTEGER PRIMARY KEY AUTOINCREMENT, uid TEXT NOT NULL UNIQUE, code TEXT, name TEXT, displayName TEXT, created TEXT, lastUpdated TEXT, shortName TEXT, displayShortName TEXT, description TEXT, displayDescription TEXT, periodType TEXT, categoryCombo TEXT NOT NULL, mobile INTEGER, version INTEGER, expiryDays INTEGER, timelyDays INTEGER, notifyCompletingUser INTEGER, openFuturePeriods INTEGER, fieldCombinationRequired INTEGER, validCompleteOnly INTEGER, noValueRequiresComment INTEGER, skipOffline INTEGER, dataElementDecoration INTEGER, renderAsTabs INTEGER, renderHorizontally INTEGER, accessDataWrite INTEGER, workflow TEXT, color TEXT, icon TEXT, FOREIGN KEY (categoryCombo) REFERENCES CategoryCombo (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED);
CREATE TABLE DataSetDataElementLink (_id INTEGER PRIMARY KEY AUTOINCREMENT, dataSet TEXT NOT NULL, dataElement TEXT NOT NULL, categoryCombo TEXT REFERENCES CategoryCombo (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (dataSet) REFERENCES DataSet (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (dataElement) REFERENCES DataElement (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, UNIQUE (dataSet, dataElement));
CREATE TABLE Indicator (_id INTEGER PRIMARY KEY AUTOINCREMENT, uid TEXT NOT NULL UNIQUE, code TEXT, name TEXT, displayName TEXT, created TEXT, lastUpdated TEXT, shortName TEXT, displayShortName TEXT, description TEXT, displayDescription TEXT, annualized INTEGER, indicatorType TEXT, numerator TEXT, numeratorDescription TEXT, denominator TEXT, denominatorDescription TEXT, url TEXT, FOREIGN KEY (indicatorType) REFERENCES IndicatorType (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED);
CREATE TABLE DataSetIndicatorLink (_id INTEGER PRIMARY KEY AUTOINCREMENT, dataSet TEXT NOT NULL, indicator TEXT NOT NULL, FOREIGN KEY (dataSet) REFERENCES DataSet (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (indicator) REFERENCES Indicator (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, UNIQUE (dataSet, indicator));
CREATE TABLE Period (_id INTEGER PRIMARY KEY AUTOINCREMENT, periodId TEXT, periodType TEXT, startDate TEXT, endDate TEXT, UNIQUE (periodId));
CREATE TABLE ValueTypeDeviceRendering (_id INTEGER PRIMARY KEY AUTOINCREMENT, uid TEXT, objectTable TEXT, deviceType TEXT, type TEXT, min INTEGER, max INTEGER, step INTEGER, decimalPoints INTEGER, UNIQUE (uid, deviceType));
CREATE TABLE Note (_id INTEGER PRIMARY KEY AUTOINCREMENT, noteType TEXT, event TEXT, enrollment TEXT, value TEXT, storedBy TEXT, storedDate TEXT, uid TEXT, state TEXT, deleted INTEGER, FOREIGN KEY (event) REFERENCES Event (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (enrollment) REFERENCES Enrollment (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, UNIQUE (noteType, event, enrollment, value, storedBy, storedDate));
CREATE TABLE Legend (_id INTEGER PRIMARY KEY AUTOINCREMENT, uid TEXT NOT NULL UNIQUE, code TEXT, name TEXT, displayName TEXT, created TEXT, lastUpdated TEXT, startValue REAL, endValue REAL, color TEXT, legendSet TEXT, FOREIGN KEY (legendSet) REFERENCES LegendSet (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED);
CREATE TABLE LegendSet (_id INTEGER PRIMARY KEY AUTOINCREMENT, uid TEXT NOT NULL UNIQUE, code TEXT, name TEXT, displayName TEXT, created TEXT, lastUpdated TEXT, symbolizer TEXT);
CREATE TABLE ProgramIndicatorLegendSetLink (_id INTEGER PRIMARY KEY AUTOINCREMENT, programIndicator TEXT NOT NULL, legendSet TEXT NOT NULL, FOREIGN KEY (programIndicator) REFERENCES ProgramIndicator (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (legendSet) REFERENCES LegendSet (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, UNIQUE (programIndicator, legendSet));
CREATE TABLE SystemSetting (_id INTEGER PRIMARY KEY AUTOINCREMENT, key TEXT, value TEXT, UNIQUE (key));
CREATE TABLE ProgramSectionAttributeLink (_id INTEGER PRIMARY KEY AUTOINCREMENT, programSection TEXT NOT NULL, attribute TEXT NOT NULL, sortOrder INTEGER, FOREIGN KEY (programSection) REFERENCES ProgramSection (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (attribute) REFERENCES TrackedEntityAttribute (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, UNIQUE (programSection, attribute));
CREATE TABLE TrackedEntityAttributeReservedValue (_id INTEGER PRIMARY KEY AUTOINCREMENT, ownerObject TEXT, ownerUid TEXT, key TEXT, value TEXT, created TEXT, expiryDate TEXT, organisationUnit TEXT, temporalValidityDate TEXT);
CREATE TABLE CategoryOptionComboCategoryOptionLink (_id INTEGER PRIMARY KEY AUTOINCREMENT, categoryOptionCombo TEXT NOT NULL, categoryOption TEXT NOT NULL, FOREIGN KEY (categoryOptionCombo) REFERENCES CategoryOptionCombo (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (categoryOption) REFERENCES CategoryOption (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, UNIQUE (categoryOptionCombo, categoryOption));
CREATE TABLE Section (_id INTEGER PRIMARY KEY AUTOINCREMENT, uid TEXT NOT NULL UNIQUE, code TEXT, name TEXT, displayName TEXT, created TEXT, lastUpdated TEXT, description TEXT, sortOrder INTEGER, dataSet TEXT NOT NULL, showRowTotals INTEGER, showColumnTotals INTEGER, FOREIGN KEY (dataSet) REFERENCES DataSet (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED);
CREATE TABLE SectionDataElementLink (_id INTEGER PRIMARY KEY AUTOINCREMENT, section TEXT NOT NULL, dataElement TEXT NOT NULL, sortOrder INTEGER, FOREIGN KEY (section) REFERENCES Section (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (dataElement) REFERENCES DataElement (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, UNIQUE (section, dataElement));
CREATE TABLE DataSetCompulsoryDataElementOperandsLink (_id INTEGER PRIMARY KEY AUTOINCREMENT, dataSet TEXT NOT NULL, dataElementOperand TEXT NOT NULL, FOREIGN KEY (dataSet) REFERENCES DataSet (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (dataElementOperand) REFERENCES DataElementOperand (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, UNIQUE (dataSet, dataElementOperand));
CREATE TABLE DataInputPeriod (_id INTEGER PRIMARY KEY AUTOINCREMENT, dataSet TEXT NOT NULL, period TEXT NOT NULL, openingDate TEXT, closingDate TEXT, FOREIGN KEY (dataSet) REFERENCES DataSet (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED);
CREATE TABLE RelationshipConstraint (_id INTEGER PRIMARY KEY AUTOINCREMENT, relationshipType TEXT NOT NULL, constraintType TEXT NOT NULL, relationshipEntity TEXT, trackedEntityType TEXT, program TEXT, programStage TEXT, FOREIGN KEY (trackedEntityType) REFERENCES TrackedEntityType (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (program) REFERENCES Program (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (programStage) REFERENCES ProgramStage (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, UNIQUE (relationshipType, constraintType));
CREATE TABLE RelationshipItem (_id INTEGER PRIMARY KEY AUTOINCREMENT, relationship TEXT NOT NULL, relationshipItemType TEXT NOT NULL, trackedEntityInstance TEXT, enrollment TEXT, event TEXT, FOREIGN KEY (relationship) REFERENCES Relationship (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED  FOREIGN KEY (trackedEntityInstance) REFERENCES TrackedEntityInstance (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (enrollment) REFERENCES Enrollment (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (event) REFERENCES Event (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED);
CREATE TABLE OrganisationUnitGroup (_id INTEGER PRIMARY KEY AUTOINCREMENT, uid TEXT NOT NULL UNIQUE, code TEXT, name TEXT, displayName TEXT, created TEXT, lastUpdated TEXT, shortName TEXT, displayShortName TEXT);
CREATE TABLE OrganisationUnitOrganisationUnitGroupLink (_id INTEGER PRIMARY KEY AUTOINCREMENT, organisationUnit TEXT NOT NULL, organisationUnitGroup TEXT NOT NULL, FOREIGN KEY (organisationUnit) REFERENCES OrganisationUnit (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (organisationUnitGroup) REFERENCES OrganisationUnitGroup (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, UNIQUE (organisationUnit, organisationUnitGroup));
CREATE TABLE ProgramStageDataElement (_id INTEGER PRIMARY KEY AUTOINCREMENT, uid TEXT NOT NULL UNIQUE, code TEXT, name TEXT, displayName TEXT, created TEXT, lastUpdated TEXT, displayInReports INTEGER, compulsory INTEGER, allowProvidedElsewhere INTEGER, sortOrder INTEGER, allowFutureDate INTEGER, dataElement TEXT NOT NULL, programStage TEXT NOT NULL, FOREIGN KEY (programStage) REFERENCES ProgramStage (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (dataElement) REFERENCES DataElement (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED);
CREATE TABLE ProgramStageSectionDataElementLink (_id INTEGER PRIMARY KEY AUTOINCREMENT, programStageSection TEXT NOT NULL, dataElement TEXT NOT NULL, sortOrder INTEGER NOT NULL, FOREIGN KEY (programStageSection) REFERENCES ProgramStageSection (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (dataElement) REFERENCES DataElement (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED);
CREATE TABLE DataElementOperand (_id INTEGER PRIMARY KEY AUTOINCREMENT, uid TEXT NOT NULL UNIQUE, dataElement TEXT, categoryOptionCombo TEXT, FOREIGN KEY (dataElement) REFERENCES DataElement (uid) DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (categoryOptionCombo) REFERENCES CategoryOptionCombo (uid) DEFERRABLE INITIALLY DEFERRED);
CREATE TABLE IndicatorType (_id INTEGER PRIMARY KEY AUTOINCREMENT, uid TEXT NOT NULL UNIQUE, code TEXT, name TEXT, displayName TEXT, created TEXT, lastUpdated TEXT, number INTEGER, factor INTEGER);
CREATE TABLE ForeignKeyViolation (_id INTEGER PRIMARY KEY AUTOINCREMENT, fromTable TEXT, fromColumn TEXT, toTable TEXT, toColumn TEXT, notFoundValue TEXT, fromObjectUid TEXT, fromObjectRow TEXT, created TEXT);
CREATE TABLE D2Error (_id INTEGER PRIMARY KEY AUTOINCREMENT, resourceType TEXT, uid TEXT, url TEXT, errorComponent TEXT, errorCode TEXT, errorDescription TEXT, httpErrorCode INTEGER, created TEXT);
CREATE TABLE Authority (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT);
CREATE TABLE TrackedEntityTypeAttribute (_id INTEGER PRIMARY KEY AUTOINCREMENT, trackedEntityType TEXT, trackedEntityAttribute TEXT, displayInList INTEGER, mandatory INTEGER, searchable INTEGER, sortOrder INTEGER, FOREIGN KEY (trackedEntityType) REFERENCES TrackedEntityType (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (trackedEntityAttribute) REFERENCES TrackedEntityAttribute (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED);
CREATE TABLE Relationship (_id INTEGER PRIMARY KEY AUTOINCREMENT, uid TEXT NOT NULL UNIQUE, name TEXT, created TEXT, lastUpdated TEXT, relationshipType TEXT NOT NULL, state TEXT, deleted INTEGER, FOREIGN KEY (relationshipType) REFERENCES RelationshipType (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED);
CREATE TABLE DataElement (_id INTEGER PRIMARY KEY AUTOINCREMENT, uid TEXT NOT NULL UNIQUE, code TEXT, name TEXT, displayName TEXT, created TEXT, lastUpdated TEXT, shortName TEXT, displayShortName TEXT, description TEXT, displayDescription TEXT, valueType TEXT, zeroIsSignificant INTEGER, aggregationType TEXT, formName TEXT, domainType TEXT, displayFormName TEXT, optionSet TEXT, categoryCombo TEXT NOT NULL, fieldMask TEXT, color TEXT, icon TEXT, FOREIGN KEY (optionSet) REFERENCES OptionSet (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (categoryCombo) REFERENCES CategoryCombo (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED);
CREATE TABLE OptionGroup (_id INTEGER PRIMARY KEY AUTOINCREMENT, uid TEXT NOT NULL UNIQUE, code TEXT, name TEXT, displayName TEXT, created TEXT, lastUpdated TEXT, optionSet TEXT NOT NULL, FOREIGN KEY (optionSet) REFERENCES OptionSet (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED);
CREATE TABLE OptionGroupOptionLink (_id INTEGER PRIMARY KEY AUTOINCREMENT, optionGroup TEXT NOT NULL, option TEXT NOT NULL, FOREIGN KEY (optionGroup) REFERENCES OptionGroup (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (option) REFERENCES Option (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, UNIQUE (optionGroup, option));
CREATE TABLE ProgramRuleAction (_id INTEGER PRIMARY KEY AUTOINCREMENT, uid TEXT NOT NULL UNIQUE, code TEXT, name TEXT, displayName TEXT, created TEXT, lastUpdated TEXT, data TEXT, content TEXT, location TEXT, trackedEntityAttribute TEXT, programIndicator TEXT, programStageSection TEXT, programRuleActionType TEXT, programStage TEXT, dataElement TEXT, programRule TEXT NOT NULL, option TEXT, optionGroup TEXT, FOREIGN KEY (programRule) REFERENCES ProgramRule (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (trackedEntityAttribute) REFERENCES TrackedEntityAttribute (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (programIndicator) REFERENCES ProgramIndicator (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (programStageSection) REFERENCES ProgramStageSection (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (programStage) REFERENCES ProgramStage (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (dataElement) REFERENCES DataElement (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (option) REFERENCES Option (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (optionGroup) REFERENCES OptionGroup (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED);
CREATE TABLE OrganisationUnitLevel (_id INTEGER PRIMARY KEY AUTOINCREMENT, uid TEXT NOT NULL UNIQUE, code TEXT, name TEXT, displayName TEXT, created TEXT, lastUpdated TEXT, level INTEGER);
CREATE TABLE ProgramSection (_id INTEGER PRIMARY KEY AUTOINCREMENT, uid TEXT NOT NULL UNIQUE, code TEXT, name TEXT, displayName TEXT, created TEXT, lastUpdated TEXT, description TEXT, program TEXT, sortOrder INTEGER, formName TEXT, color TEXT, icon TEXT, FOREIGN KEY (program) REFERENCES Program (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED);
CREATE TABLE DataApproval (_id INTEGER PRIMARY KEY AUTOINCREMENT, workflow TEXT NOT NULL, organisationUnit TEXT NOT NULL, period TEXT NOT NULL, attributeOptionCombo TEXT NOT NULL, state TEXT, FOREIGN KEY (attributeOptionCombo) REFERENCES CategoryOptionCombo (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (period) REFERENCES Period (periodId), FOREIGN KEY (organisationUnit) REFERENCES OrganisationUnit (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, UNIQUE (attributeOptionCombo, period, organisationUnit, workflow));
CREATE TABLE TrackedEntityAttribute (_id INTEGER PRIMARY KEY AUTOINCREMENT, uid TEXT NOT NULL UNIQUE, code TEXT, name TEXT, displayName TEXT, created TEXT, lastUpdated TEXT, shortName TEXT, displayShortName TEXT, description TEXT, displayDescription TEXT, pattern TEXT, sortOrderInListNoProgram INTEGER, optionSet TEXT, valueType TEXT, expression TEXT, programScope INTEGER, displayInListNoProgram INTEGER, generated INTEGER, displayOnVisitSchedule INTEGER, orgunitScope INTEGER, uniqueProperty INTEGER, inherit INTEGER, formName TEXT, displayFormName TEXT, fieldMask TEXT, color TEXT, icon TEXT, FOREIGN KEY (optionSet) REFERENCES OptionSet (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED);
CREATE TABLE TrackerImportConflict (_id INTEGER PRIMARY KEY AUTOINCREMENT, conflict TEXT, value TEXT, trackedEntityInstance TEXT, enrollment TEXT, event TEXT, tableReference TEXT, errorCode TEXT, status TEXT, created TEXT, FOREIGN KEY (trackedEntityInstance) REFERENCES TrackedEntityInstance (uid) DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (enrollment) REFERENCES Enrollment (uid) DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (event) REFERENCES Event (uid) DEFERRABLE INITIALLY DEFERRED);
CREATE TABLE DataSetOrganisationUnitLink (_id INTEGER PRIMARY KEY AUTOINCREMENT, dataSet TEXT NOT NULL, organisationUnit TEXT NOT NULL, FOREIGN KEY (dataSet) REFERENCES DataSet (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (organisationUnit) REFERENCES OrganisationUnit (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, UNIQUE (organisationUnit, dataSet));
CREATE TABLE UserOrganisationUnit (_id INTEGER PRIMARY KEY AUTOINCREMENT, user TEXT NOT NULL, organisationUnit TEXT NOT NULL, organisationUnitScope TEXT NOT NULL, root INTEGER, FOREIGN KEY (user) REFERENCES User (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (organisationUnit) REFERENCES OrganisationUnit (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, UNIQUE (organisationUnitScope, user, organisationUnit));
CREATE TABLE ProgramOrganisationUnitLastUpdated (_id INTEGER PRIMARY KEY AUTOINCREMENT, program TEXT NOT NULL, organisationUnit TEXT NOT NULL, lastSynced TEXT, FOREIGN KEY (program) REFERENCES Program (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (organisationUnit) REFERENCES OrganisationUnit (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, UNIQUE (organisationUnit, program));
CREATE TABLE RelationshipType (_id INTEGER PRIMARY KEY AUTOINCREMENT, uid TEXT NOT NULL UNIQUE, code TEXT, name TEXT, displayName TEXT, created TEXT, lastUpdated TEXT, fromToName TEXT, toFromName TEXT, bidirectional INTEGER, accessDataWrite INTEGER );
CREATE TABLE ProgramStage (_id INTEGER PRIMARY KEY AUTOINCREMENT, uid TEXT NOT NULL UNIQUE, code TEXT, name TEXT, displayName TEXT, created TEXT, lastUpdated TEXT, executionDateLabel TEXT, allowGenerateNextVisit INTEGER, validCompleteOnly INTEGER, reportDateToUse TEXT, openAfterEnrollment INTEGER, repeatable INTEGER, formType TEXT, displayGenerateEventBox INTEGER, generatedByEnrollmentDate INTEGER, autoGenerateEvent INTEGER, sortOrder INTEGER, hideDueDate INTEGER, blockEntryForm INTEGER, minDaysFromStart INTEGER, standardInterval INTEGER, program TEXT NOT NULL, periodType TEXT, accessDataWrite INTEGER, remindCompleted INTEGER, description TEXT, displayDescription TEXT, featureType TEXT, color TEXT, icon TEXT, enableUserAssignment INTEGER, FOREIGN KEY (program) REFERENCES Program (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED);
CREATE TABLE Program (_id INTEGER PRIMARY KEY AUTOINCREMENT, uid TEXT NOT NULL UNIQUE, code TEXT, name TEXT, displayName TEXT, created TEXT, lastUpdated TEXT, shortName TEXT, displayShortName TEXT, description TEXT, displayDescription TEXT, version INTEGER, onlyEnrollOnce INTEGER, enrollmentDateLabel TEXT, displayIncidentDate INTEGER, incidentDateLabel TEXT, registration INTEGER, selectEnrollmentDatesInFuture INTEGER, dataEntryMethod INTEGER, ignoreOverdueEvents INTEGER, selectIncidentDatesInFuture INTEGER, useFirstStageDuringRegistration INTEGER, displayFrontPageList INTEGER, programType TEXT, relatedProgram TEXT, trackedEntityType TEXT, categoryCombo TEXT, accessDataWrite INTEGER, expiryDays INTEGER, completeEventsExpiryDays INTEGER, expiryPeriodType TEXT, minAttributesRequiredToSearch INTEGER, maxTeiCountToReturn INTEGER, featureType TEXT, accessLevel TEXT, color TEXT, icon TEXT, FOREIGN KEY (trackedEntityType) REFERENCES TrackedEntityType (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (categoryCombo) REFERENCES CategoryCombo (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED);
CREATE TABLE TrackedEntityInstance (_id INTEGER PRIMARY KEY AUTOINCREMENT, uid TEXT NOT NULL UNIQUE, created TEXT, lastUpdated TEXT, createdAtClient TEXT, lastUpdatedAtClient TEXT, organisationUnit TEXT, trackedEntityType TEXT, geometryType TEXT, geometryCoordinates TEXT, state TEXT, deleted INTEGER, FOREIGN KEY (organisationUnit) REFERENCES OrganisationUnit (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (trackedEntityType) REFERENCES TrackedEntityType (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED);
CREATE TABLE Enrollment (_id INTEGER PRIMARY KEY AUTOINCREMENT, uid TEXT NOT NULL UNIQUE, created TEXT, lastUpdated TEXT, createdAtClient TEXT, lastUpdatedAtClient TEXT, organisationUnit TEXT NOT NULL, program TEXT NOT NULL, enrollmentDate TEXT, incidentDate TEXT, followup INTEGER, status TEXT, trackedEntityInstance TEXT NOT NULL, state TEXT, geometryType TEXT, geometryCoordinates TEXT, deleted INTEGER, FOREIGN KEY (organisationUnit) REFERENCES OrganisationUnit (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (program) REFERENCES Program (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (trackedEntityInstance) REFERENCES TrackedEntityInstance (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED);
CREATE TABLE Event (_id INTEGER PRIMARY KEY AUTOINCREMENT, uid TEXT NOT NULL UNIQUE, enrollment TEXT, created TEXT, lastUpdated TEXT, createdAtClient TEXT, lastUpdatedAtClient TEXT, status TEXT, geometryType TEXT, geometryCoordinates TEXT, program TEXT NOT NULL, programStage TEXT NOT NULL, organisationUnit TEXT NOT NULL, eventDate TEXT, completedDate TEXT, dueDate TEXT, state TEXT, attributeOptionCombo TEXT, deleted INTEGER, assignedUser TEXT, FOREIGN KEY (program) REFERENCES Program (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (programStage) REFERENCES ProgramStage (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (enrollment) REFERENCES Enrollment (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (organisationUnit) REFERENCES OrganisationUnit (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (attributeOptionCombo) REFERENCES CategoryOptionCombo (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED);
CREATE TABLE DataValue (_id INTEGER PRIMARY KEY AUTOINCREMENT, dataElement TEXT NOT NULL, period TEXT NOT NULL, organisationUnit TEXT NOT NULL, categoryOptionCombo TEXT NOT NULL, attributeOptionCombo TEXT NOT NULL, value TEXT, storedBy TEXT, created TEXT, lastUpdated TEXT, comment TEXT, followUp INTEGER, state TEXT, deleted INTEGER, FOREIGN KEY (dataElement) REFERENCES DataElement (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (period) REFERENCES Period (periodId), FOREIGN KEY (organisationUnit) REFERENCES OrganisationUnit (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (categoryOptionCombo) REFERENCES CategoryOptionCombo (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (attributeOptionCombo) REFERENCES CategoryOptionCombo (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, UNIQUE (dataElement, period, organisationUnit, categoryOptionCombo, attributeOptionCombo));
CREATE TABLE TrackedEntityDataValue (_id INTEGER PRIMARY KEY AUTOINCREMENT, event TEXT NOT NULL, dataElement TEXT NOT NULL, storedBy TEXT, value TEXT, created TEXT, lastUpdated TEXT, providedElsewhere INTEGER, FOREIGN KEY (event) REFERENCES Event (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (dataElement) REFERENCES DataElement (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED);
CREATE TABLE TrackedEntityAttributeValue (_id INTEGER PRIMARY KEY AUTOINCREMENT, created TEXT, lastUpdated TEXT, value TEXT, trackedEntityAttribute TEXT NOT NULL, trackedEntityInstance TEXT NOT NULL, FOREIGN KEY (trackedEntityAttribute) REFERENCES trackedEntityAttribute (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (trackedEntityInstance) REFERENCES TrackedEntityInstance (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED);
CREATE TABLE FileResource (_id INTEGER PRIMARY KEY AUTOINCREMENT, uid TEXT NOT NULL UNIQUE, name TEXT, created TEXT, lastUpdated TEXT, contentType TEXT, contentLength INTEGER, path TEXT, state TEXT);
CREATE TABLE DataSetCompleteRegistration (_id INTEGER PRIMARY KEY AUTOINCREMENT, period TEXT NOT NULL, dataSet TEXT NOT NULL, organisationUnit TEXT NOT NULL, attributeOptionCombo TEXT, date TEXT, storedBy TEXT, state TEXT, deleted INTEGER, FOREIGN KEY (dataSet) REFERENCES DataSet (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (period) REFERENCES Period (periodId) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (organisationUnit) REFERENCES OrganisationUnit (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (attributeOptionCombo) REFERENCES CategoryOptionCombo (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, UNIQUE (period, dataSet, organisationUnit, attributeOptionCombo));
CREATE TABLE SectionGreyedFieldsLink (_id INTEGER PRIMARY KEY AUTOINCREMENT, section TEXT NOT NULL, dataElementOperand TEXT NOT NULL, categoryOptionCombo TEXT, FOREIGN KEY (section) REFERENCES Section (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (dataElementOperand) REFERENCES DataElementOperand (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (categoryOptionCombo) REFERENCES CategoryOptionCombo (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, UNIQUE (section, dataElementOperand, categoryOptionCombo));
CREATE TABLE AuthenticatedUser (_id INTEGER PRIMARY KEY AUTOINCREMENT, user TEXT NOT NULL UNIQUE, hash TEXT, FOREIGN KEY (user) REFERENCES User (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED);
CREATE UNIQUE INDEX event_data_element ON TrackedEntityDataValue(event, dataElement);
CREATE UNIQUE INDEX tracked_entity_instance_attribute ON TrackedEntityAttributeValue(trackedEntityInstance, trackedEntityAttribute);
CREATE TABLE GeneralSetting (_id INTEGER PRIMARY KEY AUTOINCREMENT, dataSync TEXT, encryptDB INTEGER, lastUpdated TEXT, metadataSync TEXT, reservedValues INTEGER, numberSmsToSend TEXT, numberSmsConfirmation TEXT);
CREATE TABLE DataSetSetting (_id INTEGER PRIMARY KEY AUTOINCREMENT, uid TEXT UNIQUE, name TEXT, lastUpdated TEXT, periodDSDownload INTEGER, periodDSDBTrimming INTEGER, FOREIGN KEY (uid) REFERENCES DataSet (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED);
CREATE TABLE ProgramSetting (_id INTEGER PRIMARY KEY AUTOINCREMENT, uid TEXT UNIQUE, name TEXT, lastUpdated TEXT, teiDownload INTEGER, teiDBTrimming INTEGER, eventsDownload INTEGER, eventsDBTrimming INTEGER, updateDownload TEXT, updateDBTrimming TEXT, settingDownload TEXT, settingDBTrimming TEXT, enrollmentDownload TEXT, enrollmentDBTrimming TEXT, eventDateDownload TEXT, eventDateDBTrimming TEXT, enrollmentDateDownload TEXT, enrollmentDateDBTrimming TEXT, FOREIGN KEY (uid) REFERENCES Program (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED);