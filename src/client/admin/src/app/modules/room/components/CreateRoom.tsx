import {RoomCreateModel, SeatCreateModel, useCreateRoom} from "@/app/usecases/room.usecase.ts";
import {Box, Button, Card, CardContent, CardHeader, Drawer, IconButton, TextField} from "@mui/material";
import {Dispatch, SetStateAction} from "react";
import {IoClose} from "react-icons/io5";
import {useForm} from "react-hook-form";
import {LoadingButton} from "@mui/lab";


type RoomCreateFormValues = {
    roomNumber: number,
    rows: number,
    cols: number
}
const CreateRoom = ({onCreate, setOnCreate}: {onCreate: boolean, setOnCreate: Dispatch<SetStateAction<boolean>>}) => {
    const {mutate, isLoading} = useCreateRoom()
    const {register, getValues, setValue, handleSubmit} = useForm<RoomCreateFormValues>()
    return <>
        <Drawer
            open={onCreate}
            onClose={() => setOnCreate(false)}
            anchor={"left"}
        >
            <Card
                sx={{width: "1500px"}}
                className={"h-full"}
            >
                <CardHeader title={<div className={"flex flex-row justify-between"}>Thêm phòng mới <IconButton onClick={() => setOnCreate(false)}><IoClose size={30} /></IconButton></div>} />
                <CardContent>
                    <form onSubmit={handleSubmit(data => {
                        let tmp: SeatCreateModel[] = []
                        for (let i = 0; i < data?.rows; i++) {
                            for (let j = 0; j < data?.cols; j++) {
                                tmp.push({rowNumber: i + 1, colNumber: j + 1})
                            }
                        }
                        
                        mutate({
                            roomNumber: data?.roomNumber,
                            seats: tmp
                        }, {
                            onSuccess: () => {
                                setOnCreate(false)
                            }
                        })
                    })} className={"border-2 p-10 flex flex-row justify-between gap-5"}>
                        <TextField label={"Tên phòng "} {...register("roomNumber")} fullWidth={true} />
                        <TextField type={"number"} label={"Số hàng ghế ( hàng )"} fullWidth={true} {...register("rows")}/>
                        <TextField type={"number"} label={"Số ghế trong 1 hàng ( ghế )"} fullWidth={true} {...register("cols")} />
                        
                        

                        <LoadingButton
                            
                            type={"submit"} loading={isLoading} className={""} size={"small"} variant={"contained"}>Thêm phòng </LoadingButton>                         
                    </form>

                   {/* {Array.from({length: getValues("rows")}).map((value, index) => {
                        return <div key={index} className={"flex w-full justify-between flex-row"}>
                            {Array.from({length: getValues("cols")}).map((val, index) => {
                                return <div key={index} >{index + 1}</div>
                            })}
                        </div>
                    })}*/}
                </CardContent>
            </Card>
        </Drawer>
    </>
}

export default CreateRoom