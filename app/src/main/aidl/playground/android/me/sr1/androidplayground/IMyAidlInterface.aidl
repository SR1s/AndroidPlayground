package playground.android.me.sr1.androidplayground;

interface IMyAidlInterface {
    String getThreadNameFast();
    String getThreadNameSlow();
    String getThreadNameBlocking();
    String getThreadNameUnblock();
}
