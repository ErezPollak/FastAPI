from fastapi import FastAPI
from pydantic import BaseModel


class Item(BaseModel):
    input_string: str


app = FastAPI()

n = 0


@app.get("/n")
async def hello():
    global n
    n += 1
    return {"massage": f"hello world {n}"}


@app.post("/n")
async def set_n(item: Item):
    global n
    n = int(item.input_string)
    return {"massage": f"successfully changed n to {n}"}
