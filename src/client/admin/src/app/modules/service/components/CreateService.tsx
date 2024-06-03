import {Dispatch, memo, SetStateAction} from "react";
import {Card, CardContent, CardHeader, Drawer, IconButton, TextField} from "@mui/material";
import {IoClose} from "react-icons/io5";
import {SeatCreateModel, useCreateRoom} from "@/app/usecases/room.usecase.ts";
import {LoadingButton} from "@mui/lab";
import {useForm} from "react-hook-form";
import {ServiceCreateModel, useCreateService} from "@/app/usecases/service.usecase.ts";

const CreateService = ({onCreate, setOnCreate, refetch}: {onCreate: boolean, setOnCreate: Dispatch<SetStateAction<boolean>>, refetch: any}) => {
    const {mutate, isLoading} = useCreateService()
    const {handleSubmit, register} = useForm<ServiceCreateModel>()
    
    return <Drawer
        open={onCreate}
        onClose={() => setOnCreate(false)}
        anchor={"left"}
    >
        <Card
            sx={{width: "900px"}}
            className={"h-full"}
        >
            <CardHeader title={<div className={"flex flex-row justify-between"}>Thêm dịch vụ mới <IconButton onClick={() => setOnCreate(false)}><IoClose size={30} /></IconButton></div>} />
            <CardContent>
                <form onSubmit={handleSubmit(data => {
                    let formData = new FormData()
                    formData.append("name", data?.name)
                    formData.append("unit", data?.unit)
                    formData.append("priceUnit", data?.priceUnit.toString())
                    formData.append("priceUnit", data?.avatar[0])
                    mutate(formData, {
                        onSuccess: () => {
                            setOnCreate(false)
                            refetch()
                        }
                    })
                    
                    
                    
                })} className={"border-2 p-10 flex flex-col justify-between gap-5"}>
                    <TextField label={"Tên dịch vụ"} {...register("name")} fullWidth={true} />
                    <TextField label={"Đơn vị"} fullWidth={true} {...register("unit")}/>
                    <TextField label={"Đơn giá "} fullWidth={true} {...register("priceUnit")} />
                    <TextField type={"file"} helperText={"Hình ảnh mô tả "}  fullWidth={true} {...register("avatar")} />



                    <LoadingButton

                        type={"submit"} loading={isLoading} className={""} size={"small"} variant={"contained"}>Thêm dịch vụ</LoadingButton>
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
}

export default memo(CreateService)