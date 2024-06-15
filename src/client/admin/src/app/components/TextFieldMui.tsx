
import {Control, Controller} from "react-hook-form";
import {TextField, TextFieldProps} from "@mui/material";


type TextFieldMuiProps = TextFieldProps & {
    name: string
    control: Control<any, any>
}

const TextFieldMui = (props: TextFieldMuiProps) => {
    return <>
        <Controller control={props?.control} render={({field, fieldState, formState}) => {
            return <TextField className={`${props?.className} text-white`} {...field} {...props} onChange={(event) => field.onChange(event.target.value)}/>
        }} name={props?.name}/>
    </>
}

export default TextFieldMui
