CalEvents
=============

Android Application Sample.
It get all calendar events from content provider, output to log, and display part of event. 

[Considerations when building]
There is a following in a library that is included with the distribution of ical4j-1.0.5 .
 * backport-util-concurrent-3.1.jar
 * commons-codec-1.8.jar
 * commons-lang-2.6.jar
 * commons-logging-1.1.3.jar
 * groovy-all-2.1.1.jar   <<--- !! Note here !!
 * ical4j-1.0.5.jar

groovy-all-2.1.1.jar generates a warning in large quantities at build time in auther's development environment,  Eclipse is terminated with an out of memory by it.
To avoid this problem, it is preferable to use groovy-2.2.2-indy.jar than groovy-all-2.1.1.jar.
However, instead of placing the library folder(PROJECT_DIR/libs/), by the use by setting the classpath as external library, would avoid the problems of another new.

From the above, I did not include in the git repository of this application groovy-all-2.1.1.jar of ical4j included.
If you want to build this application, it is necessary to get the groovy-2.2.2-indy.jar on your own, use it be incorporated into the project as an external JAR. (set .classpath)

 -- get groovy --
  http://groovy.codehaus.org/Download


----------------------------------------------------------------------

Android�A�v���̃T���v���ł��B
�R���e���g�v���o�C�_����J�����_�[�̃C�x���g��S�Ď擾���āA�e�C�x���g���ꕔ��������Log�ւ̏o�͂Ɖ�ʕ\�����s���܂��B

�y�r���h���̒��ӓ_�z
ical4j-1.0.5�̔z�z���ɓ�������Ă��郉�C�u����(jar�t�@�C��)�ɂ͉��L������܂��B
 �Ebackport-util-concurrent-3.1.jar
 �Ecommons-codec-1.8.jar
 �Ecommons-lang-2.6.jar
 �Ecommons-logging-1.1.3.jar
 �Egroovy-all-2.1.1.jar   <-- �����ɒ���
 �Eical4j-1.0.5.jar

�����̂����A��҂̊J����(Eclipse with ADT)�ł�ical4j������groovy-all���g����APK�r���h���ɂ������܂���warning���o�ăr���h�����O��Eclipse���������s���ŗ�������ƍ������ƂɂȂ����B
�F�X���ׂāA�uindy��L���ɂ��Ă�groovy��������ǂ����낤�H�v�ƍl�����B

������groovy-2.2.2-indy.jar���g�����B
����Ƃ܂��Aproject������libs�ɕ��荞�񂾏ꍇ��groovy-all�̎��Ɠ��l��warning���������܂����o�čŏI�I�ɃG���[�Ńr���h�ł��Ȃ������B
������������O��jar�Ƃ���project��add�����ꍇ�A��肪��������apk�̃r���h�����������B

�ȏ�̂��Ƃ���Agroovy�Ɋւ��Ă� ical4j������jar�t�@�C����{�A�v����git���|�W�g���f�[�^�ɂ͊܂߂Ȃ��悤�ɂ����B
�{���|�W�g������source���擾���ăr���h����ꍇ�́A�ʓrgroovy-2.2.2-indy.jar����肵�ĊO��jar�Ƃ���project�Ɏ�荞���(.classpath��kind="lib"�Ŏw�肵��)�g���Ă��������B

 -- groovy�̔z�z�� --
  http://groovy.codehaus.org/Download



----------------------------------------------------------------------

��҂̊J��/�e�X�g��  (auther's development and test environment)

OS : Windows7 / Mac OS X Mavericks
IDE: Eclipse with ADT (Eclipse 4.2 / ADT v22.3.0)
Android deviceis: 
    HTCJ ISW13HT    [Android 4.0.2]
    IS03            [Android 2.2.1]

