import {Movie} from "@/domain/entities/movie.model.ts";
import {useForm} from "react-hook-form";
import {useGetMovies, useUpdateMovie} from "@/app/usecases/movie.usecase.ts";
import {useEffect, useState} from "react";
import {Box, Button, TextField} from "@mui/material";
import {DatePicker} from "@mui/x-date-pickers";
import TextFieldMui from "@/app/components/TextFieldMui.tsx";
import dayjs from "dayjs";
import DatePickerMui from "@/app/components/DatePickerMui.tsx";
import {useGetCategories} from "@/app/usecases/category.usecase.ts";
import {Category} from "@/domain/entities/category.model.ts";
import {LoadingButton} from "@mui/lab";


type MovieUpdateModel = {
    [k in keyof Omit<Movie, "createDate" | "updateDate">]: Movie[k]
}

export type UpdateMovieProps = {
    movieId: string
}


const UpdateMovie = ({movieId}: UpdateMovieProps) => {
    const {mutate, isLoading} = useUpdateMovie()
    const {register, control, handleSubmit, getValues, setValue, reset} = useForm<MovieUpdateModel>({
        defaultValues: {
            name: "",
            totalTime: 0,
            releaseDate: dayjs().toDate(),
            avatar: "",
            trailer: "",
            categories: [],
            description: ""
            
        }
    })
    const {} = useGetMovies({
        filters: [
            {
                fieldName: "Id",
                comparision: '==',
                fieldValue: movieId
            }
        ]
    }, {
        onSuccess: (data) => {
            handleResetForm(data)
        }
    })
    
    

    const [image, setImage] = useState(null);
    const [previewUrl, setPreviewUrl] = useState(null);

    const [video, setVideo] = useState(null);
    const [previewUrlVideo, setPreviewUrlVideo] = useState(null);
    
    

    const handleVideoChange = (e: any) => {
        const file = e.target.files[0];
        if (file) {
            setVideo(file);

            // Generate a preview URL for the selected video
            const url = URL.createObjectURL(file);
            // @ts-ignore
            setPreviewUrlVideo(url);
        }
    };
    
    const handleImageChange = (e: any) => {
        const file = e.target.files[0];
        if (file) {
            setImage(file);

            const reader = new FileReader();
            reader.onloadend = () => {
                // @ts-ignore
                setPreviewUrl(reader?.result!);
            };
            reader.readAsDataURL(file);
        }
    };


    const {data: categories} = useGetCategories({})
    const [onSelectCategory, setOnSelectCategory] = useState<string[]>([])
    const handleResetForm = (data: any) => {
        reset({
            name: data?.data?.data?.items[0]?.name,
            description: data?.data?.data?.items[0]?.description,
            trailer: data?.data?.data?.items[0]?.trailer,
            avatar: data?.data?.data?.items[0]?.avatar,
            totalTime: data?.data?.data?.items[0]?.totalTime,
            releaseDate: dayjs(data?.data?.data?.items[0]?.releaseDate).toDate(),

        })
        setOnSelectCategory(prevState => [...prevState, ...data?.data?.data?.items[0]?.categories.map((t: Category) => t.id)])
    }
    
    return <>
        <form className={"grid grid-cols-2 gap-5 w-full"} 
            onSubmit={handleSubmit(data => {
                let form = new FormData()
                form.append("id", movieId)
                form.append("name", data?.name)
                form.append("releaseDate", dayjs(data?.releaseDate).toString())
                form.append("totalTime", data?.totalTime.toString())
                form.append("description", data?.description)
                if (data?.avatar) {
                    form.append("avatar", data?.avatar[0])
                }
                if (data?.trailer) {
                    form.append("trailer", data?.trailer[0])
                }
                onSelectCategory.forEach(item => {
                    form.append("CategoryIds", item)
                })

                mutate({
                    movieId: movieId,
                    data: form
                })
            })}
            
        >
            <TextFieldMui control={control} name={"name"} label={"Tên phim "} fullWidth={true}/>
            <DatePickerMui control={control} name={"releaseDate"} label={"Ngày phát hành"}/>
            <TextFieldMui control={control} name={"totalTime"} label={"Thời lượng phim ( phút )"}/>
            <TextFieldMui control={control} name={"description"} label={"Mô tả"}/>
            <TextField type={"file"} helperText={"Ảnh bìa phim  "} {...register("avatar")}
                       onChange={handleImageChange}/>
            <TextField type={"file"} helperText={"Video trailer  "} {...register("trailer")} onChange={handleVideoChange} />
            {previewUrl && <img src={previewUrl} alt="Image Preview" width={200} className={"mx-auto"}/>}
            {!previewUrl &&
                <img src={`${import.meta.env.DEV ? "http://localhost:5002" : ""}${getValues("avatar")}`} alt=""
                     width={200}
                     className={"mx-auto"}/>}


            {!previewUrlVideo && getValues("trailer") &&
                <>
                    <video width="750" height="500" controls>
                    <source src={`${import.meta.env.DEV ? "http://localhost:5002" : ""}${getValues("trailer")}`}
                                type="video/mp4"/>
                    </video>
                </>
            }
            {previewUrlVideo && (
                <video controls className={"w-full"}>
                    <source src={previewUrlVideo || ""} type="video/mp4" />
                    Your browser does not support the video tag.
                </video>
            )}
            <div className={"col-span-2"}>
                
                <Box className={"flex flex-row flex-wrap border-2 p-2 gap-5 max-h-[200px] min-h-[200px] overflow-y-scroll"}>
                    {!!categories && categories?.data?.data?.items?.map((item) => {
                        return <Button className={`h-min `} variant={`${!onSelectCategory.includes(item?.id) ? "outlined" : "contained"}`} key={item?.id}

                                       onClick={() => {
                                           onSelectCategory.includes(item?.id) ? setOnSelectCategory(prevState => [...prevState.filter(e => e !== item?.id)]) : setOnSelectCategory(prevState => [...prevState, item?.id])
                                       }}
                        >{item?.name}</Button>
                    })}
                </Box>
            </div>
            <Box className={"col-span-2 flex justify-end gap-5"}>
                <Button  variant={"contained"} color={"error"} className={"w-max"}>Xoá</Button>
                <LoadingButton loading={isLoading} type={"submit"} variant={"contained"} className={"w-max"}>Cập nhật</LoadingButton>
            </Box>

        </form>


    </>
}


export default UpdateMovie