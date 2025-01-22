import java.util.Scanner;

class Ship{
    private String[] cords;
    private boolean isSunk = false;

    public Ship(String[] cords){
        this.cords = cords;
    }

    public String[] getCords(){
        return cords;
    }

    public boolean getIsSunk(){
        return isSunk;
    }

    public void setIsSunk(boolean isSunk){
        this.isSunk = isSunk;
    }

    public String toString(){
        String output = "";
        for (String cord: cords){
            output += cord + " ";
        }
        return output.trim();
    }
}

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char[][] battleship_board_player_1 = setupBoard(10, 10);
        char[][] battleship_board_player_2 = setupBoard(10, 10);
        // symbols:
        // ~ denotes the fog of war
        // O denotes a cell with your ship
        // X denotes that the ship was hit
        // M signifies a miss

        // Player 1 ship placements
        System.out.println("Player 1, place your ships on the game field");

        // print starting board
        printBoard(battleship_board_player_1);
        // Aircraft Carrier (5 cells)
        Ship aircraftCarrier_1 = new Ship(placeAircraftCarrier(battleship_board_player_1));
        // Battleship (4 cells)
        Ship battleship_1 = new Ship(placeBattleship(battleship_board_player_1));
        // Submarine (3 cells)
        Ship submarine_1 = new Ship(placeSubmarine(battleship_board_player_1));
        // Cruiser (3 cells)
        Ship cruiser_1 = new Ship(placeCruiser(battleship_board_player_1));
        // Destroyer (2 cells)
        Ship destroyer_1 = new Ship(placeDestroyer(battleship_board_player_1));

        Ship[] player_1_ships = {aircraftCarrier_1, battleship_1, submarine_1, cruiser_1, destroyer_1};

        System.out.println("Press Enter and pass the move to another player");
        scanner.nextLine();
        for (int i = 0; i < 20; i++){
            System.out.println();
        }
        // Player 2 ship placements
        System.out.println("Player 2, place your ships on the game field");

        // print starting board
        printBoard(battleship_board_player_2);
        // Aircraft Carrier (5 cells)
        Ship aircraftCarrier_2 = new Ship(placeAircraftCarrier(battleship_board_player_2));
        // Battleship (4 cells)
        Ship battleship_2 = new Ship(placeBattleship(battleship_board_player_2));
        // Submarine (3 cells)
        Ship submarine_2 = new Ship(placeSubmarine(battleship_board_player_2));
        // Cruiser (3 cells)
        Ship cruiser_2 = new Ship(placeCruiser(battleship_board_player_2));
        // Destroyer (2 cells)
        Ship destroyer_2 = new Ship(placeDestroyer(battleship_board_player_2));

        Ship[] player_2_ships = {aircraftCarrier_2, battleship_2, submarine_2, cruiser_2, destroyer_2};

        // Game Start
        int gameResult = battleship_Game(battleship_board_player_1, battleship_board_player_2, player_1_ships, player_2_ships);
        if (gameResult == 1){
            System.out.println("Player 1 wins!!");
        }
        else{
            System.out.println("Player 2 wins!!");
        }
    }

    public static int battleship_Game(char[][] battleship_board_player_1, char[][] battleship_board_player_2, Ship[] player_1_ships, Ship[] player_2_ships){
        Scanner scanner = new Scanner(System.in);
        char[][] fog_board_player_1 = setupBoard(10, 10);
        char[][] fog_board_player_2 = setupBoard(10, 10);
        // Game Loop
        int gameResult = 0;
        int player_turn = 1;
        while (gameResult == 0) {
            System.out.println("Press Enter and pass the move to another player");
            scanner.nextLine();
            for (int i = 0; i < 20; i++){
                System.out.println();
            }
            if (player_turn == 1){

                // Player 1 shot
                printBoard(fog_board_player_1);
                System.out.println("-----------");
                printBoard(battleship_board_player_1);
                System.out.println("Player 1, it's your turn:");
                String shotCords = playerShot(battleship_board_player_2, fog_board_player_1);
                boolean all_ships_sunk = checkShipsStatus(fog_board_player_1, shotCords, player_2_ships);
                if (all_ships_sunk){
                    gameResult = 1;
                }
                player_turn = 2;
            }
            else if (player_turn == 2){
                // Player 1 shot
                printBoard(fog_board_player_2);
                System.out.println("-----------");
                printBoard(battleship_board_player_2);
                System.out.println("Player 2, it's your turn:");
                String shotCords = playerShot(battleship_board_player_1, fog_board_player_2);
                boolean all_ships_sunk = checkShipsStatus(fog_board_player_2, shotCords, player_1_ships);
                if (all_ships_sunk){
                    gameResult = 2;
                }
                player_turn = 1;
            }
        }
        return gameResult;
    }

    public static boolean checkShipsStatus(char[][] fog_board, String shotCords, Ship[] opp_ships){
        if (checkHitOrMiss(fog_board, shotCords)){
            Ship aircraftCarrier = opp_ships[0];
            Ship battleship = opp_ships[1];
            Ship submarine = opp_ships[2];
            Ship cruiser = opp_ships[3];
            Ship destroyer = opp_ships[4];
            // find which ship the cords belonged too
            int shipHit = findShipHit(fog_board, shotCords, aircraftCarrier, battleship, submarine, cruiser, destroyer);
            switch (shipHit){
                case 1:
                    if (aircraftCarrier.getIsSunk() && battleship.getIsSunk() && submarine.getIsSunk() && cruiser.getIsSunk() && destroyer.getIsSunk()){
                        System.out.println("You sank the last ship. You won. Congratulations!");
                        return true;
                    }
                    else if (aircraftCarrier.getIsSunk()){
                        System.out.println("You sank a ship!");
                        return false;
                    }
                    else{
                        System.out.println("You hit a ship!");
                        return false;
                    }
                case 2:
                    if (aircraftCarrier.getIsSunk() && battleship.getIsSunk() && submarine.getIsSunk() && cruiser.getIsSunk() && destroyer.getIsSunk()){
                        System.out.println("You sank the last ship. You won. Congratulations!");
                        return true;
                    }
                    else if (battleship.getIsSunk()){
                        System.out.println("You sank a ship!");
                        return false;
                    }
                    else{
                        System.out.println("You hit a ship!");
                        return false;
                    }
                case 3:
                    if (aircraftCarrier.getIsSunk() && battleship.getIsSunk() && submarine.getIsSunk() && cruiser.getIsSunk() && destroyer.getIsSunk()){
                        System.out.println("You sank the last ship. You won. Congratulations!");
                        return true;
                    }
                    else if (submarine.getIsSunk()){
                        System.out.println("You sank a ship!");
                        return false;
                    }
                    else{
                        System.out.println("You hit a ship!");
                        return false;
                    }
                case 4:
                    if (aircraftCarrier.getIsSunk() && battleship.getIsSunk() && submarine.getIsSunk() && cruiser.getIsSunk() && destroyer.getIsSunk()){
                        System.out.println("You sank the last ship. You won. Congratulations!");
                        return true;
                    }
                    else if (cruiser.getIsSunk()){
                        System.out.println("You sank a ship!");
                        return false;
                    }
                    else{
                        System.out.println("You hit a ship!");
                        return false;
                    }
                case 5:
                    if (aircraftCarrier.getIsSunk() && battleship.getIsSunk() && submarine.getIsSunk() && cruiser.getIsSunk() && destroyer.getIsSunk()){
                        System.out.println("You sank the last ship. You won. Congratulations!");
                        return true;
                    }
                    else if (destroyer.getIsSunk()){
                        System.out.println("You sank a ship!");
                        return false;
                    }
                    else{
                        System.out.println("You hit a ship!");
                        return false;
                    }
                default:
                    System.out.println("Error in shipHit!");
                    return true;
            }
        }
        else {
            // print board and response for missing shot
            System.out.println("You missed!");
            return false;
        }
    }

    public static int findShipHit(char[][] fog_board, String shotCords, Ship aircraftCarrier, Ship battleship, Ship submarine, Ship cruiser, Ship destroyer){
        // aircraftCarrier
        for (String cord: aircraftCarrier.getCords()){
            if (cord.equals(shotCords)){
                updateShipStatus(aircraftCarrier, fog_board);
                return 1;
            }
        }
        // battleship
        for (String cord: battleship.getCords()){
            if (cord.equals(shotCords)){
                updateShipStatus(battleship, fog_board);
                return 2;
            }
        }
        // submarine
        for (String cord: submarine.getCords()){
            if (cord.equals(shotCords)){
                updateShipStatus(submarine, fog_board);
                return 3;
            }
        }
        // cruiser
        for (String cord: cruiser.getCords()){
            if (cord.equals(shotCords)){
                updateShipStatus(cruiser, fog_board);
                return 4;
            }
        }
        // destroyer
        for (String cord: destroyer.getCords()){
            if (cord.equals(shotCords)){
                updateShipStatus(destroyer, fog_board);
                return 5;
            }
        }
        return 0;
    }

    public static void updateShipStatus(Ship ship, char[][] fog_board){
        boolean all_cords_hit = true;
        for (String cord: ship.getCords()) {
            char cord_row = getRow(cord);
            int cord_colm = getColm(cord);
            if (fog_board[cord_row - 'A'][cord_colm - 1] != 'X'){
                all_cords_hit = false;
            }
        }
        if (all_cords_hit){
            ship.setIsSunk(true);
        }
    }

    public static boolean checkHitOrMiss(char[][] fog_board, String cord){
        char cord_row = getRow(cord);
        int cord_colm = getColm(cord);
        if (fog_board[cord_row - 'A'][cord_colm - 1] == 'X'){
            // HIT
            return true;
        }
        else {
            // Miss
            return false;
        }
    }

    public static String playerShot(char[][] battleship_board, char[][] fog_board) {
        Scanner scanner = new Scanner(System.in);
        // print broad then shot message
        // inf loop until valid shot is entered
        boolean valid_shot = false;
        String workingCord = "";
        while (!valid_shot) {
            // user cord for shot
            String cord = scanner.nextLine();

            // check if cord is a legal shot
            if (!legalCordCheck(cord)) {
                System.out.println("Error! You entered the wrong coordinates! Try again:");
            }
            else {
                valid_shot = true;
                workingCord = cord;
            }
        }
        return shotBoardUpdate(battleship_board, workingCord, fog_board);
    }

    public static String shotBoardUpdate(char[][] battleship_board, String cord, char[][] fog_board) {
        // check cord. if shot is 'O' mark with X as hit otherwise mark as M for miss
        char cord_row = getRow(cord);
        int cord_colm = getColm(cord);

        if (battleship_board[cord_row - 'A'][cord_colm - 1] == 'O' || battleship_board[cord_row - 'A'][cord_colm - 1] == 'X') {
            battleship_board[cord_row - 'A'][cord_colm - 1] = 'X';
            updateFogBoard(battleship_board, fog_board);
        } else {
            battleship_board[cord_row - 'A'][cord_colm - 1] = 'M';
            updateFogBoard(battleship_board, fog_board);
        }
        return cord;
    }

    public static void updateFogBoard(char[][] battleship_board, char[][] fog_board) {
        for (int i = 0; i < battleship_board.length; i++) {
            for (int j = 0; j < battleship_board.length; j++) {
                if (battleship_board[i][j] == 'X') {
                    fog_board[i][j] = 'X';
                }
                else if (battleship_board[i][j] == 'M') {
                    fog_board[i][j] = 'M';
                }
            }
        }
    }

    public static String[] placeDestroyer(char[][] battleship_board){
        Scanner scanner = new Scanner(System.in);
        boolean valid_ship = false;
        String[] ship_cords = null;
        System.out.println("Enter the coordinates of the Destroyer (2 cells):");
        while (!valid_ship) {
            // user input
            String cords = scanner.nextLine();
            String first_cord = cords.split(" ")[0];
            String second_cord = cords.split(" ")[1];

            // check if either cord is a legal cord
            if (!legalCordCheck(first_cord) || !legalCordCheck(second_cord)) {
                System.out.println("Error! Wrong ship location! Try again:");
            }
            // check if ship is valid direction or start and end point
            else if (!sameRowCheck(first_cord, second_cord)) {
                System.out.println("Error! Wrong ship location! Try again:");
            }
            // check length of ship
            else if (!checkShipLength(first_cord, second_cord, 2)) {
                System.out.println("Error! Wrong length of the Destroyer! Try again:");
            }
            // check so that ship isn't adjacent to another ship.
            else if (!checkShipAdjacent(battleship_board, returnShipCords(first_cord, second_cord, 2))) {
                System.out.println("Error! You placed it too close to another one. Try again:");
            }
            else {
                valid_ship = true;
                // print all cords the ship occupies
                // return list of all cords the ship will occupy
                setShip(battleship_board, returnShipCords(first_cord, second_cord, 2));
                ship_cords = returnShipCords(first_cord, second_cord, 2);
                printBoard(battleship_board);
            }
        }
        return ship_cords;
    }

    public static String[] placeCruiser(char[][] battleship_board){
        Scanner scanner = new Scanner(System.in);
        boolean valid_ship = false;
        String[] ship_cords = null;
        System.out.println("Enter the coordinates of the Cruiser (3 cells):");
        while (!valid_ship) {
            // user input
            String cords = scanner.nextLine();
            String first_cord = cords.split(" ")[0];
            String second_cord = cords.split(" ")[1];

            // check if either cord is a legal cord
            if (!legalCordCheck(first_cord) || !legalCordCheck(second_cord)) {
                System.out.println("Error! Wrong ship location! Try again:");
            }
            // check if ship is valid direction or start and end point
            else if (!sameRowCheck(first_cord, second_cord)) {
                System.out.println("Error! Wrong ship location! Try again:");
            }
            // check length of ship
            else if (!checkShipLength(first_cord, second_cord, 3)) {
                System.out.println("Error! Wrong length of the Cruiser! Try again:");
            }
            // check so that ship isn't adjacent to another ship.
            else if (!checkShipAdjacent(battleship_board, returnShipCords(first_cord, second_cord, 3))) {
                System.out.println("Error! You placed it too close to another one. Try again:");
            }
            else {
                valid_ship = true;
                // print all cords the ship occupies
                // return list of all cords the ship will occupy
                setShip(battleship_board, returnShipCords(first_cord, second_cord, 3));
                ship_cords = returnShipCords(first_cord, second_cord, 3);
                printBoard(battleship_board);
            }
        }
        return ship_cords;
    }

    public static String[] placeSubmarine(char[][] battleship_board){
        Scanner scanner = new Scanner(System.in);
        boolean valid_ship = false;
        String[] ship_cords = null;
        System.out.println("Enter the coordinates of the Submarine (3 cells):");
        while (!valid_ship) {
            // user input
            String cords = scanner.nextLine();
            String first_cord = cords.split(" ")[0];
            String second_cord = cords.split(" ")[1];

            // check if either cord is a legal cord
            if (!legalCordCheck(first_cord) || !legalCordCheck(second_cord)) {
                System.out.println("Error! Wrong ship location! Try again:");
            }
            // check if ship is valid direction or start and end point
            else if (!sameRowCheck(first_cord, second_cord)) {
                System.out.println("Error! Wrong ship location! Try again:");
            }
            // check length of ship
            else if (!checkShipLength(first_cord, second_cord, 3)) {
                System.out.println("Error! Wrong length of the Submarine! Try again:");
            }
            // check so that ship isn't adjacent to another ship.
            else if (!checkShipAdjacent(battleship_board, returnShipCords(first_cord, second_cord, 3))) {
                System.out.println("Error! You placed it too close to another one. Try again:");
            }
            else {
                valid_ship = true;
                // print all cords the ship occupies
                // return list of all cords the ship will occupy
                setShip(battleship_board, returnShipCords(first_cord, second_cord, 3));
                ship_cords = returnShipCords(first_cord, second_cord, 3);
                printBoard(battleship_board);
            }
        }
        return ship_cords;
    }

    public static String[] placeBattleship(char[][] battleship_board){
        Scanner scanner = new Scanner(System.in);
        boolean valid_ship = false;
        String[] ship_cords = null;
        System.out.println("Enter the coordinates of the Battleship (4 cells):");
        while (!valid_ship) {
            // user input
            String cords = scanner.nextLine();
            String first_cord = cords.split(" ")[0];
            String second_cord = cords.split(" ")[1];

            // check if either cord is a legal cord
            if (!legalCordCheck(first_cord) || !legalCordCheck(second_cord)) {
                System.out.println("Error! Wrong ship location! Try again:");
            }
            // check if ship is valid direction or start and end point
            else if (!sameRowCheck(first_cord, second_cord)) {
                System.out.println("Error! Wrong ship location! Try again:");
            }
            // check length of ship
            else if (!checkShipLength(first_cord, second_cord, 4)) {
                System.out.println("Error! Wrong length of the Battleship! Try again:");
            }
            // check so that ship isn't adjacent to another ship.
            else if (!checkShipAdjacent(battleship_board, returnShipCords(first_cord, second_cord, 4))) {
                System.out.println("Error! You placed it too close to another one. Try again:");
            }
            else {
                valid_ship = true;
                // print all cords the ship occupies
                // return list of all cords the ship will occupy
                setShip(battleship_board, returnShipCords(first_cord, second_cord, 4));
                ship_cords = returnShipCords(first_cord, second_cord, 4);
                printBoard(battleship_board);
            }
        }
        return ship_cords;
    }

    public static String[] placeAircraftCarrier(char[][] battleship_board){
        Scanner scanner = new Scanner(System.in);
        boolean valid_ship = false;
        String[] ship_cords = null;
        System.out.println("Enter the coordinates of the Aircraft Carrier (5 cells):");
        while (!valid_ship) {
            // user input
            String cords = scanner.nextLine();
            String first_cord = cords.split(" ")[0];
            String second_cord = cords.split(" ")[1];

            // check if either cord is a legal cord
            if (!legalCordCheck(first_cord) || !legalCordCheck(second_cord)) {
                System.out.println("Error! Wrong ship location! Try again:");
            }
            // check if ship is valid direction or start and end point
            else if (!sameRowCheck(first_cord, second_cord)) {
                System.out.println("Error! Wrong ship location! Try again:");
            }
            // check length of ship
            else if (!checkShipLength(first_cord, second_cord, 5)) {
                System.out.println("Error! Wrong length of the Aircraft Carrier! Try again:");
            }
            // check so that ship isn't adjacent to another ship.
            else if (!checkShipAdjacent(battleship_board, returnShipCords(first_cord, second_cord, 5))) {
                System.out.println("Error! You placed it too close to another one. Try again:");
            }
            else {
                valid_ship = true;
                // print all cords the ship occupies
                // return list of all cords the ship will occupy
                setShip(battleship_board, returnShipCords(first_cord, second_cord, 5));
                ship_cords = returnShipCords(first_cord, second_cord, 5);
                printBoard(battleship_board);
            }
        }
        return ship_cords;
    }

    public static void setShip(char[][] battleship_board, String[] shipCords) {
        for (String cord: shipCords) {
            char row_cord = getRow(cord);
            int colm_cord = getColm(cord);
            battleship_board[row_cord-'A'][colm_cord-1] = 'O';
        }
    }

    public static boolean checkShipAdjacent(char[][] battleship_board, String[] shipCords){
        for (String cord: shipCords) {
            char row_cord = getRow(cord);
            int colm_cord = getColm(cord);
            // check exact location
            if (battleship_board[row_cord-'A'][colm_cord-1] == 'O') {
                return false;
            }
            // check above, below, left, right
            // above
            try {
                if (battleship_board[row_cord-'A'-1][colm_cord-1] == 'O') {
                    return false;
                }
            } catch (ArrayIndexOutOfBoundsException e) {}
            // below
            try {
                if (battleship_board[row_cord-'A'+1][colm_cord-1] == 'O') {
                    return false;
                }
            } catch (ArrayIndexOutOfBoundsException e) {}
            // left
            try {
                if (battleship_board[row_cord-'A'][colm_cord-1-1] == 'O') {
                    return false;
                }
            } catch (ArrayIndexOutOfBoundsException e) {}
            // right
            try {
                if (battleship_board[row_cord-'A'][colm_cord-1+1] == 'O') {
                    return false;
                }
            } catch (ArrayIndexOutOfBoundsException e) {}
        }
        return true;
    }

    public static String[] returnShipCords(String first_cord, String second_cord, int size) {
        String[] shipCords = new String[size];
        char first_cord_row = getRow(first_cord);
        int first_cord_colm = getColm(first_cord);

        char second_cord_row = getRow(second_cord);
        int second_cord_colm = getColm(second_cord);

        if (first_cord_row == second_cord_row) {
            // if first_cord_colm < second_cord_colm
            if (first_cord_colm < second_cord_colm) {
                for (int i = first_cord_colm; i < second_cord_colm + 1; i++) {
                    shipCords[i-first_cord_colm] = Character.toString(first_cord_row) + i;
                }
            }
            else if (first_cord_colm > second_cord_colm){
                for (int i = first_cord_colm; i >= second_cord_colm; i--) {
                    shipCords[i - first_cord_colm + size - 1] = Character.toString(first_cord_row) + i;
                }
            }
        }
        else {
            // if first_cord_rows < second_cord_rows
            if (first_cord_row < second_cord_row) {
                for (char i = first_cord_row; i < second_cord_row + 1; i++) {
                    shipCords[i - first_cord_row] = i + "" + first_cord_colm;
                }
            }
            else if (first_cord_row > second_cord_row){
                for (char i = first_cord_row; i >= second_cord_row; i--) {
                    shipCords[i - first_cord_row + size - 1] = i + "" + first_cord_colm;
                }
            }
        }
        return shipCords;
    }

    // check ship distance
    public static boolean checkShipLength(String first_cord, String second_cord, int shipLength) {
        char first_cord_row = getRow(first_cord);
        int first_cord_colm = getColm(first_cord);

        char second_cord_row = getRow(second_cord);
        int second_cord_colm = getColm(second_cord);

        // check difference in colms
        if (first_cord_row == second_cord_row) {
            int inputtedLength = Math.abs(first_cord_colm - second_cord_colm) + 1;
            return (inputtedLength == shipLength);
        }
        // check difference in the rows
        else {
            int inputtedLength = Math.abs(first_cord_row - second_cord_row) + 1;
            return (inputtedLength == shipLength);
        }
    }

    public static boolean legalCordCheck(String cord){
        char cord_row = getRow(cord);
        int cord_colm = getColm(cord);

        if (cord_row < 'A' || cord_row > 'J' ||
                cord_colm < 1 || cord_colm > 10) {
            return false;
        }
        else {
            return true;
        }
    }

    public static boolean sameRowCheck(String first_cord, String second_cord) {
        if ((getRow(first_cord) == getRow(second_cord) && getColm(first_cord) != getColm(second_cord)) ||
                (getRow(first_cord) != getRow(second_cord) && getColm(first_cord) == getColm(second_cord))) {
            return true;
        }
        else{
            return false;
        }
    }

    public static char getRow(String cord){
        char[] cordArray = cord.toCharArray();
        return cordArray[0];
    }

    public static int getColm(String cord){
        return Integer.parseInt(cord.substring(1));
    }

    public static void printBoard(char[][] board) {
        char[] letterArray = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (int i = 0; i < board.length; i++) {
            System.out.print(letterArray[i] + " ");
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static char[][] setupBoard(int row, int col) {
        char[][] newBoard = new char[row][col];
        // fill out battleship_board with ~'s
        for (int i = 0; i < newBoard.length; i++) {
            for (int j = 0; j < newBoard[i].length; j++) {
                newBoard[i][j] = '~';
            }
        }
        return newBoard;
    }
}
