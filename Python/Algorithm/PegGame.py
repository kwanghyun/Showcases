# Brute Force Algorithm for Peg Solitaire
import json

def initial(n, skip):
    """Construct triangle solitaire game with n*(n+1)/2 holes each filled
    with a peg except for a specific omitted value
    bruteForce.initial(5,(3,3))
    """

    board = {}

    # column [0, 2, 4, 6, 8]
    # row [0, 1, 2, 3, 4]
    for column in range(0, 2*n, 2):
        d = 0
        for row in range((2*n-column)/2):
            board[(column+d,row)] = True
            print (column+d,row)
            d += 1

    if skip in board:
        board[skip] = False
    return board
    # print json.dumps(board, indent=4, separators=(',',': '))