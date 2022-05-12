cdX = [1, 0, -1, 0]
cdY = [0, 1, 0, -1]

BOARD_WIDTH = 40
BOARD_HEIGHT = 20

from random import randint


class Point:
    def __init__(self):
        self.x = 0
        self.y = 0

    def __init__(self, x, y):
        self.x = x
        self.y = y

    def move(self, d):
        self.x = round_width_to_board(self.x + cdX[d])
        self.y = round_height_to_board(self.y + cdY[d])

    def rand_point(self):
        self.x = randint(0, BOARD_WIDTH)
        self.y = randint(0, BOARD_HEIGHT)

    def __eq__(self, o: object) -> bool:
        return self.x == o.x and self.y == o.y

    def __str__(self) -> str:
        return f"{self.x} {self.y}"


def round_height_to_board(y):
    if y == -1:
        return BOARD_HEIGHT
    if y == BOARD_HEIGHT + 1:
        return 0
    return y


def round_width_to_board(x):
    if x == -1:
        return BOARD_WIDTH
    if x == BOARD_WIDTH + 1:
        return 0
    return x
