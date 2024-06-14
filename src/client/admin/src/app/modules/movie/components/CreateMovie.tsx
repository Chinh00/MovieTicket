import {Box, Button, Drawer, styled, TextField, Typography} from "@mui/material";
import {Dispatch, SetStateAction, useState} from "react";
import {useCreateMovie} from "@/app/usecases/movie.usecase.ts";
import {Movie} from "@/domain/entities/movie.model.ts";
import {useForm} from "react-hook-form";
import {DatePicker, DateTimePicker} from "@mui/x-date-pickers";
import dayjs from "dayjs";
import {useGetCategories} from "@/app/usecases/category.usecase.ts";
import {LoadingButton} from "@mui/lab";

type MovieCreateModel = {
    [k in keyof Omit<Movie, "id" | "createDate" | "updateDate">]: Movie[k]
}

const CreateMovie = ({onCreate, setOnCreate}: {onCreate: boolean, setOnCreate: Dispatch<SetStateAction<boolean>>}) => {
    const {mutate, isLoading} = useCreateMovie()
    const {handleSubmit, register, getValues, setValue} = useForm<MovieCreateModel>()
    const {data} = useGetCategories({})
    const [onSelectCategory, setOnSelectCategory] = useState<string[]>([])
    return <>
        <Drawer
            anchor={"left"}
            open={onCreate}
            onClose={() => setOnCreate(false)}
        >
            <Box
                sx={{width: "600px"}}
            >
                <Typography variant={"h5"} align={"center"}>Thêm mới phim </Typography>
                <form onSubmit={handleSubmit(value => {
                    let form = new FormData()
                    form.append("id", "")
                    form.append("name", value?.name)
                    form.append("releaseDate", dayjs(value?.releaseDate).toString())
                    form.append("totalTime", value.totalTime.toString())
                    form.append("description", value?.description)
                    form.append("avatar", value?.avatar[0])
                    form.append("trailer", value?.trailer[0])
                    onSelectCategory.forEach(item => {
                        form.append("CategoryIds", item)
                    })
                    mutate(form, {
                        onSuccess: () => {
                            setOnCreate(false)
                        }
                    })
                    console.log(value)
                })} className={"border-2 p-10 m-5 grid grid-cols-1 gap-5"}>
                    <TextField {...register("name")} label={"Tên phim "} fullWidth={true} />
                    <  
                        DatePicker 
                        format={"DD/MM/YYYY"}
                        label="Ngày phát hành " {...register("releaseDate")}  
                        onChange={value => {
                            if (!!value ) {
                                setValue("releaseDate", value.toDate())
                            }
                            return value
                        }}
                    />
                    <TextField type={"number"} {...register("totalTime")} label={"Thời lượng phim ( phút )"} fullWidth={true} />
                    <TextField {...register("description")} label={"Mô tả "} fullWidth={true} />
                    <TextField type={"file"} helperText={"Ảnh bìa phim  "} {...register("avatar")}/>
                    <TextField type={"file"} helperText={"Video trailer  "} {...register("trailer")} />
                    <Box className={"flex flex-row flex-wrap border-2 p-2 gap-5 max-h-[200px] overflow-y-scroll"}>
                        {!!data && data?.data?.data?.items?.map((item) => {
                            return <Button disabled={onSelectCategory.includes(item?.id)} variant={"outlined"} key={item?.id}
                            
                                onClick={() => {
                                    onSelectCategory.includes(item?.id) ? setOnSelectCategory(prevState => [...prevState.filter(e => e !== item?.id)]) : setOnSelectCategory(prevState => [...prevState, item?.id])
                                }}
                            >{item?.name}</Button>
                        })}
                    </Box>
                    <LoadingButton loading={isLoading} type={"submit"} variant={"contained"}> Thêm mới </LoadingButton>
                </form>
            </Box>
        </Drawer>
    </>
}
export default CreateMovie

