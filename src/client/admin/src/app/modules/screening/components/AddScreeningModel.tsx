import {memo, useEffect, useState} from "react";
import {Box, Button, Modal, Typography} from "@mui/material";
import {useCreateScreening} from "@/app/usecases/screening.usecase.ts";
import {useGetMovies} from "@/app/usecases/movie.usecase.ts";
import dayjs from "dayjs";
import {DateTimePicker, MobileDateTimePicker} from "@mui/x-date-pickers";
import vi from "dayjs/locale/vi"
import { renderTimeViewClock } from '@mui/x-date-pickers/timeViewRenderers';
import {LoadingButton} from "@mui/lab";
export type AddScreeningModelProps = {
    roomId: string,
    retry: any
}
export type SelectModel = { 
    movieId: string,
    startDate: Date
}

dayjs.locale(vi)
const AddScreeningModel = ({roomId, retry}: AddScreeningModelProps) => {
    const [openModal, setOpenModal] = useState(false)
    const {mutate, isLoading} = useCreateScreening()
    const {data} = useGetMovies()
    const [selectModel, setSelectModel] = useState<SelectModel>({
        movieId: "",
        startDate: new Date()
    })
    const [openSelectTime, setOpenSelectTime] = useState(false)
    return <>
        <Button onClick={() => setOpenModal(true)}>Add Screening</Button>
        <Modal open={openModal}>
            <Box className={"w-[700px] flex flex-col gap-5 absolute top-1/2 left-1/2 -translate-x-1/2 max-h-[900px]  -translate-y-1/2 bg-white p-5 rounded-md"}>
                <Typography align={"center"} variant={"h5"} className={""}>Thêm lịch chiếu phim rạp 01</Typography>
                <div className={"flex flex-row justify-between "}>
                    <DateTimePicker
                        onAccept={(e) => {
                            setSelectModel(prevState => ({...prevState, startDate: e?.toDate()!}))
                        }}
                        open={openSelectTime}
                        disabled={!selectModel.movieId}
                        onOpen={() => setOpenSelectTime(true)}
                        onClose={() => setOpenSelectTime(false)}
                        label="Chọn thời gian bắt đầu "
                        viewRenderers={{
                            hours: renderTimeViewClock,
                            minutes: renderTimeViewClock,
                            seconds: renderTimeViewClock,
                        }}
                    />
                   
                    <Button variant={"contained"} color={"error"} onClick={() => {
                        setOpenModal(false)
                    }}>Hủy</Button>
                </div>
                
                <Box display={ "flex"} flexDirection={"column"} gap={3} className={"mt-6 overflow-y-scroll max-h-[700px]" }>
                    {!!data && data?.data?.data?.items?.map((value, index) => {
                        return <div
                            onClick={() => {
                                setSelectModel(prevState => ({
                                    ...prevState,
                                    movieId: value?.id
                                }))
                            }}
                            key={index} className={`flex ${selectModel?.movieId === value?.id && "bg-gray-300"} cursor-pointer w-full justify-start items-start gap-5 border-2 p-3`}>
                            <img src={value?.avatar}  alt={value?.name} width={100}/>
                            <div className={" font-bold  flex flex-col"}>
                                <div className={"text-blue-600 text-xl"}>
                                    {value?.name}
                                </div>
                                <div>
                                    {value?.description}
                                </div>
                                <div>
                                    Ngày phát hành {dayjs(value?.releaseDate).format("DD/MM/YYYY")}
                                </div>
                            </div>
                            
                        </div>
                    })}
                    
                </Box>
                <LoadingButton loading={isLoading} onClick={() => {
                    mutate({
                        roomId: roomId,
                        movieId: selectModel.movieId,
                        startDate: new Date(dayjs(selectModel.startDate).format("YYYY/MM/DD HH:mm:ss").toString())
                    }, {
                        onSuccess: () => {
                            retry && retry()
                            setOpenModal(false)
                        }
                    })
                    
                }} variant={"contained"}>Xác nhận</LoadingButton>
            </Box>
        </Modal>
    </>
}

export default memo(AddScreeningModel)