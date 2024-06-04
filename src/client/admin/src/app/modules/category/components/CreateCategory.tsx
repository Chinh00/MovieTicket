import {Dispatch, memo, SetStateAction} from "react";
import {Card, CardContent, CardHeader, Drawer, IconButton, TextField} from "@mui/material";
import {IoClose} from "react-icons/io5";
import {SeatCreateModel, useCreateRoom} from "@/app/usecases/room.usecase.ts";
import {LoadingButton} from "@mui/lab";
import {useForm} from "react-hook-form";
import {ServiceCreateModel, useCreateService} from "@/app/usecases/service.usecase.ts";
import {CategoryCreateModel, useCreateCategory} from "@/app/usecases/category.usecase.ts";

const CreateCategory = ({onCreate, setOnCreate, refetch}: {onCreate: boolean, setOnCreate: Dispatch<SetStateAction<boolean>>, refetch: any}) => {
    const {mutate, isLoading} = useCreateCategory()
    const {handleSubmit, register} = useForm<CategoryCreateModel>()

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
                     mutate({...data}, {
                         onSuccess: () => {
                             refetch()
                             setOnCreate(false)
                         }
                     })


                })} className={"border-2 p-10 flex flex-col justify-between gap-5"}>
                    <TextField label={"Tên danh mục"} {...register("name")} fullWidth={true} />
                    <LoadingButton

                        type={"submit"} loading={isLoading} className={""} size={"small"} variant={"contained"}>Thêm dịch vụ</LoadingButton>
                </form>

               
            </CardContent>
        </Card>
    </Drawer>
}

export default memo(CreateCategory)