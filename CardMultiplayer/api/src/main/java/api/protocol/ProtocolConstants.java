package api.protocol;

public interface ProtocolConstants {
    /* Server */
    String USER_NAME_REQUEST = "USERNAMEREQUEST";
    String USER_HAND = "USERHAND";
    String TABLE_CARDS = "TABLECARDS";
    String YOUR_TURN = "YOURTURN";
    String VALID_MOVE = "VALIDMOVE";
    String INVALID_MOVE = "INVALIDMOVE";
    String ROUND_END = "ROUNDEND";
    String SET_END = "SETEND";
    String GAME_END = "GAMEEND";
    String INFO_TO_ALL = "INFOTOALL";

    /* User */
    String USER_NAME_RESPONSE = "USERNAMERESPONSE";
    String PLAYED_CARD = "PLAYEDCARD";
}
