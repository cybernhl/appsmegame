package weking.lib.game.utils;

public class GameAction {
    public abstract class Five<T1, T2, T3, T4, T5> {
        public Five() {
        }

        public abstract void invoke(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5);
    }

    public abstract class Four<T1, T2, T3, T4> {
        public Four() {
        }

        public abstract void invoke(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4);
    }

    public abstract class Three<T1, T2, T3> {
        public Three() {
        }

        public abstract void invoke(T1 paramT1, T2 paramT2, T3 paramT3);
    }

    public abstract class Two<T1, T2> {
        public Two() {
        }

        public abstract void invoke(T1 paramT1, T2 paramT2);
    }

    public abstract class OnResult<T> {
        public OnResult() {
        }

        public abstract void invoke(T paramT);

        public abstract void onError();
    }

    public abstract class One<T> {
        public One() {
        }

        public abstract void invoke(T paramT);
    }

    public abstract class Void {
        public Void() {
        }

        public abstract void invoke();
    }
}



/* Location:           C:\Users\Administrator\Desktop\jar\classes.jar

 * Qualified Name:     weking.lib.game.utils.GameAction

 * JD-Core Version:    0.7.0.1

 */