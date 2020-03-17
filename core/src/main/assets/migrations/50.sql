ALTER TABLE Event RENAME TO Event_50;
CREATE TABLE Event (_id INTEGER PRIMARY KEY AUTOINCREMENT, uid TEXT NOT NULL UNIQUE, enrollment TEXT, created TEXT, lastUpdated TEXT, createdAtClient TEXT, lastUpdatedAtClient TEXT, status TEXT, latitude TEXT, longitude TEXT, program TEXT NOT NULL, programStage TEXT NOT NULL, organisationUnit TEXT NOT NULL, eventDate TEXT, completedDate TEXT, dueDate TEXT, state TEXT, attributeOptionCombo TEXT, FOREIGN KEY (program) REFERENCES Program (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (programStage) REFERENCES ProgramStage (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (enrollment) REFERENCES Enrollment (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (organisationUnit) REFERENCES OrganisationUnit (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED, FOREIGN KEY (attributeOptionCombo) REFERENCES CategoryOptionCombo (uid) ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED);
INSERT INTO Event (_id, uid, enrollment, created, lastUpdated, createdAtClient, lastUpdatedAtClient, status, latitude, longitude, program, programStage, organisationUnit, eventDate, completedDate, dueDate, state, attributeOptionCombo) SELECT _id, uid, enrollment, created, lastUpdated, createdAtClient, lastUpdatedAtClient, status, latitude, longitude, program, programStage, organisationUnit, eventDate, completedDate, dueDate, state, attributeOptionCombo FROM Event_50;
DROP TABLE Event_50;