cdX = [1, 0, -1, 0]
cdY = [0, 1, 0, -1]


class Point:
    def __init__(self):
        self.x = 0
        self.y = 0

    def __init__(self, x, y):
        self.x = x
        self.y = y

    def move(self, d):
        self.x = self.x + cdX[d]
        self.y = self.y + cdY[d]


def next_point(p: Point, d):
    new_p = Point(p.x + cdX[d], p.y + cdY[d])
    return new_p
